package main.com.ecoMove.Services.implementations;

import main.com.ecoMove.Services.contracts.TicketServiceInterface;
import main.com.ecoMove.enums.TicketStatus;
import main.com.ecoMove.enums.TransportType;
import main.com.ecoMove.models.Contract;
import main.com.ecoMove.models.Ticket;
import main.com.ecoMove.repositories.implementations.ContractRepository;
import main.com.ecoMove.repositories.implementations.PartnerRepository;
import main.com.ecoMove.repositories.implementations.TicketRepository;
import main.com.ecoMove.utils.EnumHelper;
import main.com.ecoMove.utils.Helper;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

public class TicketService implements TicketServiceInterface {

    private ContractRepository contractRepository;
    private TicketRepository ticketRepository;
    private Scanner scanner;
    public TicketService() throws SQLException, IOException {
        this.contractRepository = new ContractRepository();
        this.ticketRepository = new TicketRepository();
        this.scanner = new Scanner(System.in);
    }


    @Override
    public void getTickets() throws SQLException {
        ArrayList<Ticket> tickets = ticketRepository.getTickets();

        if (tickets.isEmpty()) {
            System.out.println("No tickets found.");
            return;
        }

        String leftAlignFormat = "| %-36s | %-36s | %-15s | %-15s | %-15s | %-10s | %-15s |%n";
        System.out.format("+--------------------------------------+--------------------------------------+-----------------+-----------------+-----------------+---------------------------+-----------------+%n");
        System.out.format("| Ticket ID                            | Contract ID                         | Transport Type   | Purchase Price  | Sale Price      | Sold Date                 | Ticket Status   |%n");
        System.out.format("+--------------------------------------+--------------------------------------+-----------------+-----------------+-----------------+---------------------------+-----------------+%n");

        for (Ticket ticket : tickets) {
            System.out.format(
                    leftAlignFormat,
                    ticket.getId(),
                    ticket.getContract().getId(),
                    ticket.getTransportType(),
                    ticket.getPurchasePrice(),
                    ticket.getSalePrice(),
                    ticket.getSoldDate(),
                    ticket.getTicketStatus()
            );
        }
        System.out.format("+--------------------------------------+--------------------------------------+-----------------+-----------------+-----------------+------------+-----------------+%n");

    }

    @Override
    public void addTickets() throws SQLException {
        System.out.println("Enter contract ID:");
        String contractId = scanner.nextLine();
        Contract contract = contractRepository.getContractById(contractId);

        if (contract == null) {
            System.out.println("No contract found with that ID.");
            return;
        }

        System.out.println("Enter transport type (BUS/TRAIN/PLANE/etc.):");
        TransportType transportType = EnumHelper.handleTransportationEnums(scanner);

        System.out.println("Enter purchase price:");
        double purchasePrice = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

            System.out.println("Enter sale price:");
        double salePrice = scanner.nextDouble();
        scanner.nextLine();

        TicketStatus ticketStatus = EnumHelper.handleTicketStatus(scanner);

        Ticket ticket = new Ticket(
                Helper.generateUniqueId(),
                contract,
                transportType,
                purchasePrice,
                salePrice,
                ticketStatus,
                null
                );

        try {
            ticketRepository.addTicket(ticket);
            System.out.println("Ticket added successfully.");
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    @Override
    public void updateTickets() throws SQLException {
        this.getTickets();
        while (true) {
            System.out.println("Please enter the ID of the ticket you would like to update:");
            String id = scanner.nextLine();

            try {
                Ticket ticket = ticketRepository.getTicketById(id);

                if (ticket != null) {
                    System.out.println("Updating ticket details ------------------------------------------------------------------------------------------");

                    System.out.println("Current Transport Type: " + ticket.getTransportType());
                    TransportType transportType = EnumHelper.handleTransportationEnumsUpdate(scanner, ticket.getTransportType());


                    // Update Purchase Price
                    System.out.println("Current Purchase Price: " + ticket.getPurchasePrice());
                    System.out.println("Enter new Purchase Price (or press Enter to keep the current one):");
                    String purchasePriceInput = scanner.nextLine();
                    double purchasePrice = ticket.getPurchasePrice();
                    if (!purchasePriceInput.isEmpty()) {
                        purchasePrice = Double.parseDouble(purchasePriceInput);
                    }

                    // Update Sale Price
                    System.out.println("Current Sale Price: " + ticket.getSalePrice());
                    System.out.println("Enter new Sale Price (or press Enter to keep the current one):");
                    String salePriceInput = scanner.nextLine();
                    double salePrice = ticket.getSalePrice();
                    if (!salePriceInput.isEmpty()) {
                        salePrice = Double.parseDouble(salePriceInput);
                    }

                    // Update Sold Date
                    System.out.println("Current Sold Date: " + ticket.getSoldDate());
                    System.out.println("Enter new Sold Date (yyyy-mm-dd) (or press Enter to keep the current one):");
                    String soldDateInput = scanner.nextLine();
                    Timestamp soldDate = ticket.getSoldDate();
                    if (!soldDateInput.isEmpty()) {
                        soldDate = Timestamp.valueOf(soldDateInput + " 00:00:00");
                    }

                    System.out.println("Current Ticket Status: " + ticket.getTransportType());
                    TicketStatus ticketStatus = EnumHelper.handleTicketStatusUpdate(scanner, ticket.getTicketStatus());

                    Ticket updatedTicket = new Ticket(
                            id,
                            ticket.getContract(),
                            transportType,
                            purchasePrice,
                            salePrice,
                            ticketStatus,
                            soldDate
                            );

                    ticketRepository.updateTicket(updatedTicket);
                    System.out.println("Ticket updated successfully.");
                    break;
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    @Override
    public void deleteTickets() throws SQLException {
        this.getTickets();
        while (true) {
            System.out.println("Please enter the ID of the ticket you would like to delete:");
            String id = scanner.nextLine();

            try {
                Ticket ticket = ticketRepository.getTicketById(id);
                if (ticket != null) {
                    ticketRepository.deleteTicket(id);
                    System.out.println("Ticket deleted successfully.");
                    break;
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
