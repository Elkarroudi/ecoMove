package main.com.ecoMove.repositories.implementations;

import main.com.ecoMove.database.PgsqlConnection;
import main.com.ecoMove.enums.ContractStatus;
import main.com.ecoMove.enums.TicketStatus;
import main.com.ecoMove.enums.TransportType;
import main.com.ecoMove.models.Contract;
import main.com.ecoMove.models.Partner;
import main.com.ecoMove.models.Ticket;
import main.com.ecoMove.repositories.contracts.TicketRepositoryInterface;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class TicketRepository implements TicketRepositoryInterface {

    private Connection connection;
    private ContractRepository contractRepository;
    public TicketRepository() throws SQLException, IOException {
        this.connection = PgsqlConnection.getInstance().getConnection();
        this.contractRepository = new ContractRepository();
    }


    @Override
    public ArrayList<Ticket> getTickets() throws SQLException {
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        String sqlQuery = "SELECT * FROM tickets WHERE deletedAt IS NULL;";

        try {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {

                Contract contract = contractRepository.getContractById(resultSet.getString("contractId"));
                Ticket ticket = new Ticket(
                        resultSet.getString("id"),
                        contract,
                        TransportType.valueOf(resultSet.getString("transportType")),
                        resultSet.getDouble("purchasePrice"),
                        resultSet.getDouble("salePrice"),
                        TicketStatus.valueOf(resultSet.getString("ticketStatus")),
                        resultSet.getTimestamp("soldDate")
                        );
                tickets.add(ticket);
            }
        }
        catch(SQLException e){ System.err.println("SQLException: " + e.getMessage()); }
        return tickets;
    }

    @Override
    public Ticket getTicketById(String id) throws SQLException {
        String query = "SELECT * FROM tickets WHERE id = ? ";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, id);

        ResultSet res = stmt.executeQuery();
        if(res.next()){
            Contract contract = contractRepository.getContractById(res.getString("contractId"));
            return new Ticket(
                    res.getString("id"),
                    contract,
                    TransportType.valueOf(res.getString("transportType")),
                    res.getDouble("purchasePrice"),
                    res.getDouble("salePrice"),
                    TicketStatus.valueOf(res.getString("ticketStatus")),
                    res.getTimestamp("soldDate")
            );
        }
        else{
            System.out.println("no Contracts with that id is available");
            return null;
        }
    }

    @Override
    public void addTicket(Ticket ticket) throws SQLException {
        String query = "INSERT INTO tickets (id, contractid, transporttype, purchaseprice, saleprice, ticketstatus) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, ticket.getId());
            stmt.setString(2, ticket.getContract().getId());
            stmt.setObject(3, ticket.getTransportType().name(), java.sql.Types.OTHER);
            stmt.setDouble(4, ticket.getPurchasePrice());
            stmt.setDouble(5, ticket.getSalePrice());
            stmt.setObject(6, ticket.getTicketStatus().name(), java.sql.Types.OTHER);
            stmt.executeUpdate();
        }
        catch(SQLException e){ System.err.println("SQLException: " + e.getMessage()); }
    }

    @Override
    public void updateTicket(Ticket ticket) throws SQLException {
        String query = "UPDATE tickets SET transporttype = ?, purchaseprice = ?, saleprice = ?, solddate = ?, ticketstatus = ?  WHERE id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setObject(1, ticket.getTransportType().name(), java.sql.Types.OTHER);
            stmt.setDouble(2, ticket.getPurchasePrice());
            stmt.setDouble(3, ticket.getSalePrice());
            stmt.setDate(4, new Date(ticket.getSoldDate().getTime()));
            stmt.setObject(5, ticket.getTicketStatus().name(), java.sql.Types.OTHER);
            stmt.setString(6, ticket.getId());
            stmt.executeUpdate();
        }
        catch(SQLException e){ System.err.println("SQLException: " + e.getMessage()); }
    }

    @Override
    public void deleteTicket(String id) throws SQLException {
        String query = "UPDATE tickets SET deletedat = NOW() WHERE id = ? ";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
        catch(SQLException e){ System.err.println("SQLException: " + e.getMessage()); }
    }
}
