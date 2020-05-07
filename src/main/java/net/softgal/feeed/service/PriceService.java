package net.softgal.feeed.service;

import net.softgal.feeed.domain.Price;

import java.util.List;
import java.util.Set;

public interface PriceService {

    List<Price> getAll();
    void updatePrices(Set<Price> prices);
}
