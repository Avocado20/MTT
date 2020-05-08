package net.softgal.feeed.service;

import net.softgal.feeed.domain.Price;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class PriceServiceImpl implements PriceService {
    private ReentrantLock lock = new ReentrantLock();

    private Map<String, Price> prices = new TreeMap<>();

    @Override
    public List<Price> getAll() {
        lock.lock();
        try {
            return new ArrayList<>(prices.values());
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void updatePrices(Set<Price> pricesToUpdate) {
        lock.lock();
        try {
            for (Price price : pricesToUpdate) {
                System.out.println("Updated price: " + price);
                prices.put(price.getInstrument(), price);
            }
        } finally {
            lock.unlock();
        }
    }
}
