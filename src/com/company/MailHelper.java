package com.company;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.Properties;

public class MailHelper {

    private Credentials credentials = getCredentials();

    private static final String PROPERTIES_FOLDER_PATH = "src/com/company/resources/";
    private static final String LOCAL_PROPERTIES_FILE_NAME = "resources.properties";

    private static Properties props = new PropertyReader(getPropertiesFilePath()).getProperties();

    private Credentials getCredentials() {
        Credentials creds = new Credentials();

        String host = props.getProperty("host") != null ? props.getProperty("host") : "";
        String userName = props.getProperty("username") != null ? props.getProperty("username") : "";
        String password = props.getProperty("password") != null ? props.getProperty("password") : "";

        creds.setHost(host);
        creds.setUserName(userName);
        creds.setPassword(password);

        return creds;
    }

    private static String getPropertiesFilePath() {
        return PROPERTIES_FOLDER_PATH + LOCAL_PROPERTIES_FILE_NAME;
    }

    Message[] getAllMessages() throws Exception {
        Thread.sleep(3000);
        Properties properties = new Properties();

        properties.put("mail.pop3.host", credentials.getHost());
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");

        Session emailSession = Session.getDefaultInstance(properties);

        //create the POP3 store object and connect with the pop server
        Store store = emailSession.getStore("pop3s");

        store.connect(credentials.getHost(), credentials.getUserName(), credentials.getPassword());

        //create the folder object and open it
        Folder emailFolder = store.getFolder("INBOX");
        emailFolder.open(Folder.READ_ONLY);

        // retrieve the messages from the folder in an array and print it
        return emailFolder.getMessages();
    }

    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                //result = result + "\n" + (String)bodyPart.getContent();
                result = getFinalContent(bodyPart);
                break;
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }

    String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }

        return result;
    }

    private String getFinalContent(Part p) throws MessagingException, IOException {
        String finalContents = "";
        if (p.getContent() instanceof String) {
            finalContents = (String) p.getContent();
        } else {
            Multipart mp = (Multipart) p.getContent();
            if (mp.getCount() > 0) {
                Part bp = mp.getBodyPart(0);
                try {
                    finalContents = dumpPart(bp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return finalContents.trim();
    }
    private String dumpPart(Part p) throws Exception {

        InputStream is = p.getInputStream();
        // If "is" is not already buffered, wrap a BufferedInputStream
        // around it.
        if (!(is instanceof BufferedInputStream)) {
            is = new BufferedInputStream(is);
        }
        return getStringFromInputStream(is);
    }
    private String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
