package main.com.ecoMove.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private Properties env;
    public ConfigLoader() throws IOException {
        env = new Properties();
        try (InputStream inputFile = getClass().getResourceAsStream("/main/com/ecoMove/config/env.properties")) {
            if (inputFile == null) {
                System.err.println("Sorry, unable to find env.properties");
                return;
            }
            env.load(inputFile);
        }
    }

    public String getProperty(String key) {
        return env.getProperty(key);
    }
}
