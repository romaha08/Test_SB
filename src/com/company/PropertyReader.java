package com.company;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertyReader {

    @Getter
    private Properties properties;

    public PropertyReader(String path) {
        Path configLocation = Paths.get(path);
        InputStream stream = null;
        try {
            stream = Files.newInputStream(configLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
        {
            Properties config = new Properties();
            try {
                config.load(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.properties = config;
        }
    }

}
