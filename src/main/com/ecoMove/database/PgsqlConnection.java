package main.com.ecoMove.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PgsqlConnection extends PgsqlConfig {

    private Connection connection;
    private static PgsqlConnection instance;

    public PgsqlConnection() throws IOException {

        super();
        try {
            connection = DriverManager.getConnection(this.dbUrl, this.dbUsername, this.dbPassword);
        }
        catch (SQLException e) { System.err.println("Connection Failed To Pgsql, Details : " + e.getMessage()); }
    }

    public static PgsqlConnection getInstance() throws SQLException, IOException {
        if (instance == null) { instance = new PgsqlConnection(); }
        else if (instance.getConnection().isClosed()) {
            instance = new PgsqlConnection();
        }
        return instance;
    }

    public Connection getConnection () {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error Closing Connection, Details : " + e.getMessage());
            }
        }
    }
}
