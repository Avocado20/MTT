package net.softgal.feeed.service;

import net.softgal.feeed.domain.Price;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PriceServiceImpl implements PriceService {

    private Map<String, Price> prices = new TreeMap<>();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm::ss");

    @Override
    public List<Price> getAll() {
        return new ArrayList<>(prices.values());
    }

    @Override
    public void updatePrices(Set<Price> pricesToUpdate) {
        for (Price price : pricesToUpdate) {
            Price currentPrice = prices.get(price.getInstrument());

            try {
                System.out.println("RG" + price);
                Date existingTime = PriceServiceImpl.sdf.parse(currentPrice.getTime());
                Date newTime = PriceServiceImpl.sdf.parse(price.getTime());
                if (newTime.after(existingTime)) {
                    prices.put(price.getInstrument(), price);
                }
            } catch (ParseException e) {
                System.out.println("PARSE EXCEPTION");
                continue;
            }
        }
    }
}
