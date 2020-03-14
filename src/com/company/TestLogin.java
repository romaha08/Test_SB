package com.company;

import org.testng.asserts.SoftAssert;

import javax.mail.Message;
import java.util.Arrays;
import java.util.Properties;

public class TestLogin {
    private static Properties props = new PropertyReader("src/com/company/resources/resources.properties").getProperties();
    private static String LINK = "";
    private static MailHelper mailHelper = new MailHelper();

    public void execute()
    {
        System.out.println("==================================================================");
        System.out.println("Hello, Lets start our Test Task");
        System.out.println("==================================================================");

        try {
            Message[] allMessages = mailHelper.getAllMessages();
            int counter = allMessages.length;

            SeleniumHelper.openUrl(props.getProperty("url"));
            System.out.println("\n STEP 1 executed");

            SeleniumHelper.clickForgotPassword();
            System.out.println("\n STEP 2 executed");

            SeleniumHelper.enterEmailAndResend("unbiasedtask@mail.ru");
            System.out.println("\n STEP 3/4 executed");

            System.out.println("\n Waiting for a message in mailbox...");
            allMessages = mailHelper.getAllMessages();
            Message mes = Arrays.stream(allMessages).filter(item -> item.getMessageNumber() == counter + 1).findAny().get();
            String content = mailHelper.getTextFromMessage(mes);
            LINK = content.split("<")[1].split(">")[0];
            SeleniumHelper.navigateToLink(LINK);
            System.out.println("\n STEP 5 executed");

            SeleniumHelper.generatePasswordAndGoToMyAccount();
            System.out.println("\n STEP 6 executed");

            SeleniumHelper.goToProfile();
            System.out.println("\n STEP 7/8 executed");

            SeleniumHelper.setProfileInfo();
            System.out.println("\n STEP 9 executed");

            SeleniumHelper.logOut();
            System.out.println("\n STEP 10 executed");

            SeleniumHelper.loginFromMainPage();
            System.out.println("\n STEP 11 executed");

            SoftAssert sa = SeleniumHelper.checkProfileInfo();
            System.out.println("\n STEP 12 executed");
            sa.assertAll();

        } catch (Exception | AssertionError e) {
            System.out.println("\n OPS. Something went wrong=(");
            System.out.println("\n " + e);
        } finally {
            {
                SeleniumHelper.closeDriver();
                System.out.println("\n WEB Driver stopped");
            }
        }
    }
}
