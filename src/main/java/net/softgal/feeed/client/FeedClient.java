package net.softgal.feeed.client;


import net.softgal.feeed.config.TableNameContants;
import net.softgal.feeed.domain.Price;
import net.softgal.feeed.service.PriceService;
import net.softgal.feeed.utils.PriceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

@Component
public class FeedClient implements CommandLineRunner {

    @Autowired
    private PriceService priceService;

    @Autowired
    private Environment env;

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    @Override
    public void run(String...args) throws Exception {
        this.startConnection(env.getProperty("socket.address"),Integer.valueOf(env.getProperty("socket.port")));
        this.sendMessage(env.getProperty("socket.user"));
        this.sendMessage(env.getProperty("socket.pass"));
        if (TableNameContants.ACCESS_GRANTED.equals(this.readLine())) {
            this.readRealtime();
        } else {
            throw new Exception("Cannot get access");
        }
    }

    private void readRealtime() throws InterruptedException {
        Set<Price> prices = new HashSet<>();
        while (true) {
            try {
                Price price = PriceWrapper.wrap(this.sendMessage(TableNameContants.ALL_SYMBOLS));
                prices.add(price);
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            }
            if (prices.size() > 10) {
                priceService.updatePrices(prices);
                prices.clear();
            }
            Thread.sleep(Integer.valueOf(env.getProperty("socket.message.period")));
        }
    }

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Cannot connect to socket");
        }
    }

    public String sendMessage(String msg) {
        try {
            out.println(msg);
            return in.readLine();
        } catch (IOException e) {
            System.out.println("Cannot send message");
        }
        return "";
    }

    public String readLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.out.println("Cannot read line from socket");
        }
        return "";
    }

    @PreDestroy
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        System.out.println("Disconnected");
    }

}
