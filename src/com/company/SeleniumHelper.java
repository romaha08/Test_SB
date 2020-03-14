package com.company;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class SeleniumHelper {

    private static Properties props = new PropertyReader("src/com/company/resources/resources.properties").getProperties();

    protected static WebDriver WEB_DRIVER = getDriver(props.getProperty("browser"));

    private static String password = Generator.generateCommonLangPassword();
    private static String phonePrefix = "+4420";
    private static String randomPhone = phonePrefix + Generator.generateInt(8);
    private static String postCode = "EC1V 0AU";
    private static String date = "2019-09-10";


    private static WebDriver getDriver(String browser) {
        try {
            String path = new File(".").getCanonicalPath();

            switch (browser.toLowerCase()) {
                case "chrome":
                    System.setProperty("webdriver.chrome.driver", path + "\\chromedriver.exe");
                    return new ChromeDriver();

                case "firefox":
                    System.setProperty("webdriver.firefox.marionette", path + "\\geckodriver.exe");
                    return new FirefoxDriver();

                default:
                    return new ChromeDriver();
            }
        }catch (Exception e){
            throw new AssertionError("Web driver wasn't initialized. Exception is: \n" + e.getMessage());
        }
    }

    static void closeDriver() {
        WEB_DRIVER.close();
    }

    static void openUrl(String url) {
        WEB_DRIVER.manage().window().maximize();
        WEB_DRIVER.get(url);
        Waiter.waitForElement(By.xpath(".//div[@id='app']//form[@class='v-form pa-3 pa-sm-and-up-4']"));
    }

    static void clickForgotPassword(){
        WEB_DRIVER.findElement(By.xpath(".//form//div[@class='text-xs-right mb-4']//a")).click();
    }

    static void enterEmailAndResend(String email) throws InterruptedException {
        String locator = ".//div[@class='v-input__control']//div[@class='v-text-field__slot']//input";
        Waiter.waitForElement(By.xpath(locator));
        Thread.sleep(3000);

        WEB_DRIVER.findElement(By.xpath(locator)).click();
        WEB_DRIVER.findElement(By.xpath(locator)).clear();
        WEB_DRIVER.findElement(By.xpath(locator)).sendKeys(email);
        WEB_DRIVER.findElement(By.xpath(".//form//button[@type='submit']")).click();

        Waiter.waitForElement(By.xpath(".//div[@class='pa-3 pa-sm-and-up-4']"));
    }

    static void navigateToLink(String link){
//        String a = "window.open('" + link + "','_blank');";
//        ((JavascriptExecutor)WEB_DRIVER).executeScript(a);
//        ArrayList<String> tabs = new ArrayList<String>(WEB_DRIVER.getWindowHandles());
//        WEB_DRIVER.switchTo().window(tabs.get(1));
        WEB_DRIVER.get(link);
        Waiter.waitForElement(By.xpath(".//div//form[@class='v-form pa-3 pa-sm-and-up-4']"));
    }

    static void generatePasswordAndGoToMyAccount(){
        WEB_DRIVER.findElement(By.xpath(".//div[@class='v-input__control']//input[@name='plainPassword']")).click();
        WEB_DRIVER.findElement(By.xpath(".//div[@class='v-input__control']//input[@name='plainPassword']")).clear();
        WEB_DRIVER.findElement(By.xpath(".//div[@class='v-input__control']//input[@name='plainPassword']")).sendKeys(password);

        WEB_DRIVER.findElement(By.xpath(".//div[@class='v-input__control']//input[@name='passwordConfirmation']")).click();
        WEB_DRIVER.findElement(By.xpath(".//div[@class='v-input__control']//input[@name='passwordConfirmation']")).clear();
        WEB_DRIVER.findElement(By.xpath(".//div[@class='v-input__control']//input[@name='passwordConfirmation']")).sendKeys(password);

        WEB_DRIVER.findElement(By.xpath(".//form[@class='v-form pa-3 pa-sm-and-up-4']//button")).click();

        Waiter.waitForElement(By.xpath(".//div[@class='v-list__group']"));
    }

    static void goToProfile() throws Exception {
        WEB_DRIVER.findElement(By.xpath(".//div[@class='v-list__group']//div[@class='v-list__tile__title']")).click();
        Thread.sleep(1000);
        WEB_DRIVER.findElement(By.xpath(".//div[@class='v-list__tile__content']//div[contains(text(), 'About me')]")).click();
        Thread.sleep(1500);
    }

    static void setProfileInfo() throws Exception {
        String phoneNumber = "+44 20 7946 0018";

        //WEB_DRIVER.findElement(By.xpath(".//div[@class='flex md9']//input[@name='phone']")).click();
        WEB_DRIVER.findElement(By.xpath(".//div[@class='flex md9']//input[@name='phone']")).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        WEB_DRIVER.findElement(By.xpath(".//div[@class='flex md9']//input[@name='phone']")).sendKeys(randomPhone);

        //WEB_DRIVER.findElement(By.xpath(".//div[@class='flex md9']//input[@name='postcode']")).click();
        WEB_DRIVER.findElement(By.xpath(".//div[@class='flex md9']//input[@name='postcode']")).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        WEB_DRIVER.findElement(By.xpath(".//div[@class='flex md9']//input[@name='postcode']")).sendKeys(postCode);

        getRandomGender();

        WEB_DRIVER.findElement(By.xpath(".//div[@class='v-input__control']//input[@name='birthday']")).click();
        Thread.sleep(1000);
        selectDate();

        WEB_DRIVER.findElement(By.xpath(".//div[@class='layout align-center']//button")).click();
        Thread.sleep(3000);
    }

    private static void selectDate() throws Exception {
        WEB_DRIVER.findElement(By.xpath(".//ul[@class='v-date-picker-years']//li[@class='active primary--text']")).click();

        Waiter.waitForElement(By.xpath(".//button[@class='v-btn v-btn--flat v-btn--outline theme--light accent--text'] | .//button[@class='v-btn v-btn--active v-btn--depressed theme--light accent']"));
        WEB_DRIVER.findElement(By.xpath(".//button[@class='v-btn v-btn--flat v-btn--outline theme--light accent--text'] | .//button[@class='v-btn v-btn--active v-btn--depressed theme--light accent']")).click();

        Waiter.waitForElement(By.xpath(".//button[@class='v-btn v-btn--flat v-btn--floating v-btn--outline theme--light accent--text'] | .//button[@class='v-btn v-btn--active v-btn--icon v-btn--floating theme--light accent']"));
        WEB_DRIVER.findElement(By.xpath(".//button[@class='v-btn v-btn--flat v-btn--floating v-btn--outline theme--light accent--text'] | .//button[@class='v-btn v-btn--active v-btn--icon v-btn--floating theme--light accent']")).click();
        Thread.sleep(1000);
    }

    static void logOut() throws Exception {
        WEB_DRIVER.findElement(By.xpath(".//div[@class='v-list pt-0 theme--light']//div[contains(text(), 'Log out')]")).click();
        Thread.sleep(1000);
        WEB_DRIVER.findElement(By.xpath(".//div[@class='v-card__actions']//div[contains(text(), 'Yes')]/parent::button")).click();
        Waiter.waitForElement(By.xpath(".//div[@id='app']//form[@class='v-form pa-3 pa-sm-and-up-4']"));
    }

    static void loginFromMainPage() throws Exception {
        WEB_DRIVER.findElement(By.xpath(".//div[@class='v-text-field__slot']//input[@name='email']")).click();
        WEB_DRIVER.findElement(By.xpath(".//div[@class='v-text-field__slot']//input[@name='email']")).clear();
        WEB_DRIVER.findElement(By.xpath(".//div[@class='v-text-field__slot']//input[@name='email']")).sendKeys("unbiasedtask@mail.ru");

        WEB_DRIVER.findElement(By.xpath(".//div[@class='v-text-field__slot']//input[@name='plainPassword']")).click();
        WEB_DRIVER.findElement(By.xpath(".//div[@class='v-text-field__slot']//input[@name='plainPassword']")).clear();
        WEB_DRIVER.findElement(By.xpath(".//div[@class='v-text-field__slot']//input[@name='plainPassword']")).sendKeys(password);

        WEB_DRIVER.findElement(By.xpath(".//form//button[@type='submit']")).click();
        Thread.sleep(2000);
        if(checkIfExtraPopupIsShown()){
            WebElement element = WEB_DRIVER.findElement(By.xpath(".//a[contains(text(), 'Answer later')]"));
            JavascriptExecutor executor = (JavascriptExecutor)WEB_DRIVER;
            executor.executeScript("arguments[0].click();", element);
        }
        Waiter.waitForElement(By.xpath(".//div[@class='v-list__group']"));
    }

    static Boolean checkIfExtraPopupIsShown(){
        try {
            return WEB_DRIVER.findElement(By.xpath(".//a[contains(text(), 'Answer later')]")).isDisplayed();
        }catch (Exception e){
            return false;
        }
    }

    static SoftAssert checkProfileInfo() throws Exception {
        SoftAssert sa = new SoftAssert();
        goToProfile();

        String phoneValue = WEB_DRIVER.findElement(By.xpath(".//div[@class='flex md9']//input[@name='phone']")).getAttribute("value");
        String postCodeValue = WEB_DRIVER.findElement(By.xpath(".//div[@class='flex md9']//input[@name='postcode']")).getAttribute("value");
        String dateValue = WEB_DRIVER.findElement(By.xpath(".//div[@class='v-input__control']//input[@name='birthday']")).getAttribute("value");

        sa.assertEquals(phoneValue, randomPhone, "Phone value wasn't save");
        sa.assertEquals(postCodeValue, postCode, "PostCode value wasn't save");
        sa.assertEquals(dateValue, date, "Date value wasn't save");

        return sa;
    }

    private static void getRandomGender(){
        List<WebElement> radioButtons = WEB_DRIVER.findElements(By.xpath(".//div[@class='v-radio mb-lg-and-up-0 theme--light'] | .//div[@class='v-radio mb-lg-and-up-0 theme--light accent--text']"));

        Random r = new Random();
        WebElement element = (WebElement)radioButtons.toArray()[r.nextInt(radioButtons.size())];
        element.findElement(By.xpath(".//div[@class='v-input--selection-controls__input']")).click();
    }

}
