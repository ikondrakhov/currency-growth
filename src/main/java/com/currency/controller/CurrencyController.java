package com.currency.controller;

import com.currency.services.CurrencyGrowthService;
import com.currency.exceptions.NoSuchCurrencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CurrencyController {

    @Autowired
    private CurrencyGrowthService currencyGrowthService;

    @GetMapping("/currency-growth")
    public String currencyGrowth(
            @RequestParam(value="currencyCode", defaultValue = "USD") String currencyCode,
            Model model
    ) {
        String gifUrl;
        try {
            Float difference = this.currencyGrowthService.getCurrencyDifference(currencyCode);
            gifUrl = this.currencyGrowthService.getGifUrl(difference);
        } catch (NoSuchCurrencyException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
        model.addAttribute("gifSrc", gifUrl);
        return "gif";
    }
}
