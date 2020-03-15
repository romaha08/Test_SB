package com;

import com.entity.Currency;
import com.pages.CurrencyPage;
import org.testng.annotations.Test;

import java.util.List;

public class CurrencyPageTest extends TestBase {
        private CurrencyPage currencyPage = new CurrencyPage();

    @Test
    public void currencyBlockTest() throws Exception {
        String textEtalon = "Котировки валют с 13.03.2020 23:43 по московскому времени";
        openCurrencyPage();
        List<Currency> currencyList = currencyPage.getCurrencies();
        currencyPage.checkCurrencyBlock(textEtalon)
                .checkCurrencyOnPage(currencyList.get(0));
    }

}
