package net.softgal.feeed.service;

import net.softgal.feeed.domain.Price;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PriceServiceImpl implements PriceService {

    private Map<String, Price> prices = new TreeMap<>();

    @Override
    public List<Price> getAll() {
        return new ArrayList<>(prices.values());
    }

    @Override
    public void updatePrices(Set<Price> pricesToUpdate) {
        for (Price price : pricesToUpdate) {
            prices.put(price.getInstrument(), price);
        }
    }
}
