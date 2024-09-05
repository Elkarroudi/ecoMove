package main.com.ecoMove.Services.contracts;

import java.sql.SQLException;

public interface TicketServiceInterface {

    public void getTickets() throws SQLException;
    public void addTickets() throws SQLException;
    public void updateTickets() throws SQLException;
    public void deleteTickets() throws SQLException;
}
