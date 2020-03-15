package com;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.utils.PropertyReader.getProperty;
import static com.utils.PropertyReader.readPropertiesFile;

public class Init {
    public static final long TIMEOUT_SELENIDE = 10000;

    @BeforeSuite
    protected void before() throws Throwable {
        readPropertiesFile();
        Configuration.baseUrl = "https://" + getProperty("baseUrl");
        Configuration.timeout = TIMEOUT_SELENIDE;

        setBrowser(
                getProperty("browser"),
                getProperty("environment"),
                getProperty("selenoidUrl")
        );
    }

    private static void setBrowser(String browser, String environment, String selenoidUrl) throws Exception {
        String path = new File(".").getCanonicalPath();

        if (environment.equals("local")) {
            Configuration.browser = browser;
            Configuration.startMaximized = true;

            switch (browser) {
                case "chrome":
                    Configuration.browser = "chrome";
                    System.setProperty("webdriver.chrome.driver", path + "\\chromedriver.exe");
                    System.setProperty("selenide.browser", "Chrome");
                    break;
                case "firefox":
                    break;
                default:
                    //throw new IllegalStateException("Browser " + browser + " not supported in tests");
            }
        } else {
           // Do nothing
        }
    }
}
