package com.entity;

import lombok.Getter;
import lombok.Setter;

public class Currency {
    @Getter @Setter
    private String nameOfCurrency;

    @Getter @Setter
    private Double sell;

    @Getter @Setter
    private Double buy;

    @Getter @Setter
    private Boolean currencyBuyUp;

    @Getter @Setter
    private Double currencyBuyChanged;

    @Getter @Setter
    private Boolean currencySellUp;

    @Getter @Setter
    private Double currencySellChanged;

    public Currency(){
    }

    public Currency(String nameOfCurrency, Double buy, Double sell, Boolean currencyBuyUp, Double currencyBuyChanged, Boolean currencySellUp, Double currencySellChanged){
        this.setNameOfCurrency(nameOfCurrency);
        this.setBuy(buy);
        this.setSell(sell);
        this.setCurrencyBuyUp(currencyBuyUp);
        this.setCurrencyBuyChanged(currencyBuyChanged);
        this.setCurrencySellUp(currencySellUp);
        this.setCurrencySellChanged(currencySellChanged);
    }
}
