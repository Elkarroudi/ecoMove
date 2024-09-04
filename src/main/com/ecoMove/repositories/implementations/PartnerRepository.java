package main.com.ecoMove.repositories.implementations;

import main.com.ecoMove.database.PgsqlConnection;
import main.com.ecoMove.enums.PartnerStatus;
import main.com.ecoMove.enums.TransportType;
import main.com.ecoMove.models.Partner;
import main.com.ecoMove.repositories.contracts.PartnerRepositoryInterface;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PartnerRepository implements PartnerRepositoryInterface {

    private Connection connection;
    public PartnerRepository() throws SQLException, IOException {
        this.connection = PgsqlConnection.getInstance().getConnection();
    }


    @Override
    public ArrayList<Partner> getPartners() throws SQLException {

        ArrayList<Partner> partners = new ArrayList<Partner>();
        String sqlQuery = "SELECT * FROM partners WHERE deletedAt IS NULL;";

        try {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Partner partner = new Partner(
                        resultSet.getString("id"),
                        resultSet.getString("companyName"),
                        resultSet.getString("commercialContact"),
                        TransportType.valueOf(resultSet.getString("transportType")),
                        resultSet.getString("geographicalArea"),
                        resultSet.getString("specialConditions"),
                        PartnerStatus.valueOf(resultSet.getString("partnerStatus")),
                        resultSet.getTimestamp("creationDate")
                );
                partners.add(partner);
            }
        }
        catch(SQLException e){ System.err.println("SQLException: " + e.getMessage()); }
        return partners;
    }

    @Override
    public Partner getPartnerById(String id) throws SQLException {

        String query = "SELECT * FROM partners WHERE id = ? ";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, id);

        ResultSet res = stmt.executeQuery();
        if(res.next()){
            return new Partner(
                    res.getString("id"),
                    res.getString("companyName"),
                    res.getString("commercialContact"),
                    TransportType.valueOf(res.getString("transportType")),
                    res.getString("geographicalArea"),
                    res.getString("specialConditions"),
                    PartnerStatus.valueOf(res.getString("partnerStatus")),
                    res.getTimestamp("creationDate")
            );
        }
        else{
            System.out.println("no Partner with that id is available");
            return null;
        }
    }

    @Override
    public void addPartner(Partner partner) throws SQLException {
        String query = "INSERT INTO partners (id, companyname, commercialcontact, transporttype, geographicalarea, specialconditions, partnerstatus, creationdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, partner.getId());
            stmt.setString(2, partner.getCompanyName());
            stmt.setString(3, partner.getCommercialContact());
            stmt.setObject(4, partner.getTransportType().name() ,java.sql.Types.OTHER);
            stmt.setString(5, partner.getGeographicalArea());
            stmt.setString(6, partner.getSpecialConditions());
            stmt.setObject(7, partner.getPartnerStatus().name(),java.sql.Types.OTHER);
            stmt.setTimestamp(8, new java.sql.Timestamp(partner.getCreationDate().getTime()));
            stmt.executeUpdate();
        }
        catch(SQLException e){ System.err.println("SQLException: " + e.getMessage()); }
    }

    @Override
    public void updatePartner(Partner partner) throws SQLException {
        String query = "UPDATE partners SET companyname = ?, commercialcontact = ?, transporttype = ?, geographicalarea = ?, specialconditions = ?, partnerstatus = ? WHERE id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, partner.getCompanyName());
            stmt.setString(2, partner.getCommercialContact() );
            stmt.setObject(3, partner.getTransportType().name() , java.sql.Types.OTHER );
            stmt.setString(4, partner.getGeographicalArea() );
            stmt.setString(5, partner.getSpecialConditions() );
            stmt.setObject(6, partner.getPartnerStatus() , java.sql.Types.OTHER);
            stmt.setString(7, partner.getId());
            stmt.executeUpdate();
        }
        catch(SQLException e){ System.err.println("SQLException: " + e.getMessage()); }
    }

    @Override
    public void deletePartner(String id) throws SQLException {
        String query = "UPDATE partners SET deletedat = NOW() WHERE id = ? ";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
        catch(SQLException e){ System.err.println("SQLException: " + e.getMessage()); }
    }
}
