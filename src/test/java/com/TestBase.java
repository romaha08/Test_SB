package com;

import com.pages.CurrencyPage;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

public class TestBase extends Init {

    public static CurrencyPage openCurrencyPage() {
        open(baseUrl + "/ru/quotes/currencies");

        return new CurrencyPage().waitForPageLoaded();
    }


}
