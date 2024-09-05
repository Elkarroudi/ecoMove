package main.com.ecoMove;

import main.com.ecoMove.Services.implementations.ContractService;
import main.com.ecoMove.Services.implementations.TicketService;
import main.com.ecoMove.models.Contract;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {

        TicketService ticketService = new TicketService();
        ContractService contractService = new ContractService();

        ticketService.updateTickets();
    }
}
