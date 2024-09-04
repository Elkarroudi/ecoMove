package main.com.ecoMove;

import main.com.ecoMove.Services.implementations.ContractService;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {

        ContractService contractService = new ContractService();

        contractService.updateContract();
    }
}
