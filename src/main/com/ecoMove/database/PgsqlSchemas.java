package main.com.ecoMove.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PgsqlSchemas {

    private Connection connection;
    public PgsqlSchemas(Connection connection) {
        this.connection = connection;
    }

    public static void main(String[] args) throws SQLException, IOException {

        PgsqlConnection pgsqlConnection = new PgsqlConnection();

        try (Connection connection = pgsqlConnection.getConnection()) {
            PgsqlSchemas pgsqlSchemas = new PgsqlSchemas(connection);
            pgsqlSchemas.createSchemas();
        }
        catch (SQLException e) {
            System.err.println("Error In Creating Tables, Details : " + e.getMessage());
        }
    }

    public void createSchemas() throws SQLException {
        String[] dbSchemas = {
                transportTypeEnum(), ticketStatusEnum(), contractStatusEnum(), partnerStatusEnum(), discountTypeEnum(), offerStatusEnum(),
                createPartnersTableSql(), createContractsTableSql(), createTicketsTableSql(), createOffersTableSql(),
        };

        try (Statement statement = connection.createStatement()) {
            for (String sqlQuery : dbSchemas) {
                statement.executeUpdate(sqlQuery);
            }
            System.out.println("All Tables & Enums Created Successfully!");
        }
        catch (SQLException e) { System.err.println("Error Creating Tables, Details : " + e.getMessage()); }
    }


    private String createTicketsTableSql() {
        return "CREATE TABLE IF NOT EXISTS tickets (" +
                "    id VARCHAR(255) PRIMARY KEY, " +
                "    contractId VARCHAR(255), " +
                "    transportType TransportType, " +
                "    purchasePrice DOUBLE PRECISION, " +
                "    salePrice DOUBLE PRECISION, " +
                "    soldDate TIMESTAMP, " +
                "    ticketStatus TicketStatus, " +
                "    createdAt TIMESTAMP DEFAULT current_timestamp, " +
                "    FOREIGN KEY (contractId) REFERENCES contracts(id) " +
                ");";
    }

    private String createPartnersTableSql() {
        return "CREATE TABLE IF NOT EXISTS partners (" +
                "    id VARCHAR(255) PRIMARY KEY, " +
                "    companyName VARCHAR(255), " +
                "    commercialContact VARCHAR(255), " +
                "    transportationType TransportType, " +
                "    geographicalArea VARCHAR(255), " +
                "    specialConditions TEXT, " +
                "    partnerStatus PartnerStatus, " +
                "    creationDate TIMESTAMP, " +
                "    createdAt TIMESTAMP DEFAULT current_timestamp " +
                ");";
    }

    private String createContractsTableSql() {
        return "CREATE TABLE IF NOT EXISTS contracts (" +
                "    id VARCHAR(255) PRIMARY KEY, " +
                "    partnerId VARCHAR(255), " +
                "    startDate TIMESTAMP, " +
                "    endDate  TIMESTAMP, " +
                "    specialRate DOUBLE PRECISION, " +
                "    agreementConditions VARCHAR(255), " +
                "    renewable BOOLEAN, " +
                "    contractStatus ContractStatus, " +
                "    createdAt TIMESTAMP DEFAULT current_timestamp, " +
                "    FOREIGN KEY (partnerId) REFERENCES partners(id) " +
                ");";
    }

    private String createOffersTableSql() {
        return "CREATE TABLE IF NOT EXISTS offers (" +
                "    id VARCHAR(255) PRIMARY KEY, " +
                "    contractId VARCHAR(255), " +
                "    offerName VARCHAR(255), " +
                "    offerDescription TEXT, " +
                "    startDate TIMESTAMP, " +
                "    endDate TIMESTAMP, " +
                "    discountType DiscountType, " +
                "    discountValue DOUBLE PRECISION, " +
                "    conditions TEXT, " +
                "    offerStatus OfferStatus, " +
                "    createdAt TIMESTAMP DEFAULT current_timestamp, " +
                "    FOREIGN KEY (contractId) REFERENCES contracts(id)" +
                ");";
    }

    private String transportTypeEnum() {
        return "CREATE TYPE TransportType as ENUM ('PLAN' , 'BUS' , 'TRAIN', 'TAXI');";
    }

    private String ticketStatusEnum() {
        return "CREATE TYPE TicketStatus as ENUM ('AVAILABLE' , 'SOLD' , 'CANCELLED');";
    }

    private String contractStatusEnum() {
        return "CREATE TYPE ContractStatus as ENUM ('ONGOING' , 'TERMINATED' , 'SUSPENDED');";
    }

    private String partnerStatusEnum() {
        return "CREATE TYPE PartnerStatus as ENUM ('ACTIVE' , 'EXPIRED' , 'SUSPENDED');";
    }

    private String discountTypeEnum() {
        return "CREATE TYPE DiscountType as ENUM ('PERCENTAGE' , 'FLAT');";
    }

    private String offerStatusEnum() {
        return "CREATE TYPE OfferStatus as ENUM ('ACTIVE' , 'EXPIRED' , 'SUSPENDED');";
    }
}
