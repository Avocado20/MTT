package net.softgal.feeed.controller;

import net.softgal.feeed.config.TableNameContants;
import net.softgal.feeed.domain.Price;
import net.softgal.feeed.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = TableNameContants.API_PATH + TableNameContants.PRICES)
public class ClientController {

    @Autowired
    private PriceService priceService;


    @GetMapping()
    public List<Price> getAll() {
        return priceService.getAll();

    }
}
