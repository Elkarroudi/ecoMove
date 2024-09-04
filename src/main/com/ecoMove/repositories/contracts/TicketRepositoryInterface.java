package main.com.ecoMove.repositories.contracts;

import main.com.ecoMove.models.Ticket;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TicketRepositoryInterface {

    public ArrayList<Ticket> getTickets() throws SQLException;
    public Ticket getTicketById(String id) throws SQLException;
    public void addTicket(Ticket ticket) throws SQLException;
    public void updateTicket(Ticket ticket) throws SQLException;
    public void deleteTicket(String id) throws SQLException;
}
