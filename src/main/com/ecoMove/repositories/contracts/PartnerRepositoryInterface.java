package main.com.ecoMove.repositories.contracts;

import main.com.ecoMove.models.Partner;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PartnerRepositoryInterface {

    public ArrayList<Partner> getPartners() throws SQLException;
    public Partner getPartnerById(String id) throws SQLException;
    public void addPartner(Partner partner) throws SQLException;
    public void updatePartner(Partner partner) throws SQLException;
    public void deletePartner(String id) throws SQLException;
}
