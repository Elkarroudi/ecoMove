package main.com.ecoMove.Services.contracts;

import java.sql.SQLException;

public interface PartnerServiceInterface {

    public void getPartners() throws SQLException;
    public void addPartner() throws SQLException;
    public void updatePartner() throws SQLException;
    public void deletePartner() throws SQLException;
}
