package com.utils;

import java.io.*;
import java.util.Properties;

public class PropertyReader {

    protected final static Properties prop = new Properties();

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    public static void readPropertiesFile() throws IOException {
        InputStream inputStream = PropertyReader.class.getClassLoader().getResourceAsStream("resources.properties");
        try {
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            try {
                prop.load(reader);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } finally {
            inputStream.close();
        }
    }

}
