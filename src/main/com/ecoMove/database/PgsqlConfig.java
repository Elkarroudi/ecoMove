package main.com.ecoMove.database;

import main.com.ecoMove.config.ConfigLoader;

import java.io.IOException;

public class PgsqlConfig extends ConfigLoader {

    protected String dbUrl;
    protected String dbUsername;
    protected String dbPassword;

    public PgsqlConfig() throws IOException {

        super();
        dbUrl = "jdbc:postgresql://" + getProperty("DATABASE_HOST") + ":" + getProperty("DATABASE_PORT") + "/" + getProperty("DATABASE_NAME");
        dbUsername = getProperty("DATABASE_USER");
        dbPassword = getProperty("DATABASE_PASSWORD");
    }
}
