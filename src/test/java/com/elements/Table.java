package com.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import com.utils.Waiter;

import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class Table {
    private static SelenideElement table = $x(".//table");

    public Table(String className){
        table = $x(".//table[@class = '" + className + "']");
    }

    public static SelenideElement getTable(){
        Waiter.waitForElementAppears(table);
        return table;
    }

    public ElementsCollection getAllRows(){
        if(table != null && table.is(visible)) {
            return table.$x(".//tbody").$$x(".//tr").exclude(Condition.not(visible));
        }
        return new ElementsCollection(null);
    }

    public ElementsCollection getAllCellsInRow(SelenideElement row){
        return row.findAll(By.xpath(".//td"));
    }

    public static ElementsCollection getAllRowsNames(){
        return table.$$x(".//tbody//th");
    }

    public static SelenideElement getRowByName(String rowName){
        return getAllRowsNames().findBy(text(rowName)).parent();
    }

    public static String getRowValue(String rowName){
        String result = "";
        try {
            return getRowByName(rowName).$x(".//td").getText();
        }catch (Exception | com.codeborne.selenide.ex.ElementNotFound e){
            return result;
        }
    }

    public static void checkRowText(String rowName, String rowText){
        getRowByName(rowName).$x(".//td").shouldHave(text(rowText));
    }

    public static void checkRows(Map<String, String> rowsWithValues){
        ElementsCollection allRows = getAllRowsNames();
        for(String rowName : rowsWithValues.keySet()){
            String rowValue = rowsWithValues.get(rowName);
            allRows.findBy(exactText(rowName)).parent().$x(".//td").shouldHave(text(rowValue));
        }
    }
}
