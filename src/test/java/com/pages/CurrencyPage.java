package com.pages;

import com.TestBase;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.elements.Table;
import com.entity.Currency;
import com.utils.Waiter;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$x;
import static com.utils.TestUtils.getDoubleFromString;

public class CurrencyPage extends TestBase {
    private SelenideElement PAGE_TITLE = $x(".//div[@class = 'sbrf-rich-outer']//h1");
    private Table RATES_TABLE = new Table("rates-current__table");

    public CurrencyPage waitForPageLoaded() {
        Waiter.waitForElementAppears(PAGE_TITLE);
        return this;
    }

    public void checkCurrencyOnPage(Currency currency) throws Exception {
        List<Currency> listOfCurrencies = getCurrencies();
        Assert.assertEquals(listOfCurrencies.size(), 1, "Count of Currencies on block is incorrect");
        Currency currencyFromPage = listOfCurrencies.get(0);
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(currencyFromPage.getBuy(), currency.getBuy(), "Currency: Value of 'Buy' is incorrect");
        sa.assertEquals(currencyFromPage.getCurrencyBuyUp(), currency.getCurrencyBuyUp(), "Currency: Value of 'CurrencyBuyUp' is incorrect");
        sa.assertEquals(currencyFromPage.getCurrencyBuyChanged(), currency.getCurrencyBuyChanged(), "Currency: Value of 'CurrencyBuyChanged' is incorrect");
        sa.assertEquals(currencyFromPage.getSell(), currency.getSell(), "Currency: Value of 'Sell' is incorrect");
        sa.assertEquals(currencyFromPage.getCurrencySellUp(), currency.getCurrencySellUp(), "Currency: Value of 'CurrencySellUp' is incorrect");
        sa.assertEquals(currencyFromPage.getCurrencySellChanged(), currency.getCurrencySellChanged(), "Currency: Value of 'CurrencySellChanged' is incorrect");

        sa.assertAll();
    }

    @Step("Get Currencies")
    public List<Currency> getCurrencies() throws Exception{
        List<Currency> list = new ArrayList<>();
        List<SelenideElement> ratesRow = RATES_TABLE.getAllRows().stream().filter(item -> item.getAttribute("class").equals("rates-current__table-row")).collect(Collectors.toList());
        for (SelenideElement row : ratesRow) {
            ElementsCollection ratesCells = RATES_TABLE.getAllCellsInRow(row);
            String name = ratesCells.stream().filter(item -> item.getAttribute("class").contains("column_name")).findFirst().get().getText();

            SelenideElement buyElement = ratesCells.stream().filter(item -> item.getAttribute("class").contains("column_buy")).findFirst().get();
            SelenideElement sellElement = ratesCells.stream().filter(item -> item.getAttribute("class").contains("column_sell")).findFirst().get();
            String buy = buyElement.getText();
            String textBuy = buyElement.$x(".//span").getAttribute("title");
            Boolean currencyBuyUp = getCurrencyIndexUp(textBuy);
            Double currencyChangedBuy = getCurrencyChanged(textBuy);

            String sell = sellElement.getText();
            String textSell = sellElement.$x(".//span").getAttribute("title");
            Boolean currencySellUp = getCurrencyIndexUp(textSell);
            Double currencyChangedSell = getCurrencyChanged(textSell);

            String nameFinal = name.split(" /")[0];
            Double buyFinal = getDoubleFromString(buy.split(" \\(")[0]);
            Double sellFinal = getDoubleFromString(sell.split(" \\(")[0]);
            list.add(new Currency(nameFinal, buyFinal, sellFinal, currencyBuyUp, currencyChangedBuy, currencySellUp, currencyChangedSell));
        }

        return list;
    }

    @Step("Check Currency Block")
    public CurrencyPage checkCurrencyBlock(String textOfBlock) {
        ElementsCollection rows = RATES_TABLE.getAllRows();
        SelenideElement headersRow = rows.stream().filter(item -> item.getAttribute("class").contains("header")).findFirst().get();
        List<SelenideElement> ratesRow = RATES_TABLE.getAllRows().stream().filter(item -> item.getAttribute("class").equals("rates-current__table-row")).collect(Collectors.toList());

        // Check Titles
        ElementsCollection titles = RATES_TABLE.getAllCellsInRow(headersRow);
        titles.shouldHave(CollectionCondition.texts("Валюта", "Покупка", "Продажа"));

        // Check rates
        Assert.assertEquals(ratesRow.size(), 1, "Size of default rates is incorrect");

        // Check Text
        String textFromPage = $x(".//div[@class = 'rates-current__info']").getText();
        Assert.assertEquals(textFromPage, textOfBlock, "Text at the bottom of block is incorrect");

        return this;
    }

    private Boolean getCurrencyIndexUp(String text) {
        String textOfUp = text.split(": ")[0];
        if (textOfUp.equalsIgnoreCase("повышение")) {
            return true;
        }
        return false;
    }

    private Double getCurrencyChanged(String text) throws Exception {
        String textOfChanges = text.split(": ")[1];
        return getDoubleFromString(textOfChanges);
    }
}
