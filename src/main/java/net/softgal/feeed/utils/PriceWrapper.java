package net.softgal.feeed.utils;

import net.softgal.feeed.domain.Price;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PriceWrapper {

    private static SimpleDateFormat FORMATTER = new SimpleDateFormat("HH:mm:ss");

    public static Price wrap(String rawPrice) throws IllegalArgumentException {
        List<String> price = Arrays.asList(rawPrice.split(" "));

        if (price.size() < 4) {
            throw new IllegalArgumentException("Wrong price format: " + price);
        }
        return new Price().setInstrument(price.get(0))
                .setBid(price.get(2))
                .setAsk(price.get(3))
                .setTime(FORMATTER.format(new Date()));
    }
}
