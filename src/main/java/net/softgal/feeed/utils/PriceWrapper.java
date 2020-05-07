package net.softgal.feeed.utils;

import net.softgal.feeed.domain.Price;

import java.util.Arrays;
import java.util.List;

public class PriceWrapper {

    public static Price wrap(String rawPrice) throws IllegalArgumentException {
        List<String> price = Arrays.asList(rawPrice.split(" "));

        if (price.size() < 3) {
            throw new IllegalArgumentException("Wrong price format");
        }
        return new Price().setInstrument(price.get(0)).setBid(price.get(1)).setAsk(price.get(2));
    }
}
