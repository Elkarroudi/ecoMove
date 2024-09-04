package main.com.ecoMove.repositories.implementations;

import main.com.ecoMove.database.PgsqlConnection;
import main.com.ecoMove.enums.DiscountType;
import main.com.ecoMove.enums.OfferStatus;
import main.com.ecoMove.enums.TicketStatus;
import main.com.ecoMove.enums.TransportType;
import main.com.ecoMove.models.Contract;
import main.com.ecoMove.models.Offer;
import main.com.ecoMove.models.Ticket;
import main.com.ecoMove.repositories.contracts.OfferRepositoryInterface;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class OfferRepository implements OfferRepositoryInterface {

    private Connection connection;
    private ContractRepository contractRepository;
    public OfferRepository() throws SQLException, IOException {
        this.connection = PgsqlConnection.getInstance().getConnection();
        this.contractRepository = new ContractRepository();
    }


    @Override
    public ArrayList<Offer> getOffers() throws SQLException {
        ArrayList<Offer> offers = new ArrayList<Offer>();
        String sqlQuery = "SELECT * FROM offers WHERE deletedAt IS NULL;";

        try {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {

                Contract contract = contractRepository.getContractById(resultSet.getString("contractId"));
                Offer offer = new Offer(
                        resultSet.getString("id"),
                        contract,
                        resultSet.getString("offerName"),
                        resultSet.getString("offerDescription"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate"),
                        DiscountType.valueOf(resultSet.getString("discountType")),
                        resultSet.getDouble("discountValue"),
                        resultSet.getString("conditions"),
                        OfferStatus.valueOf(resultSet.getString("offerStatus"))
                );
                offers.add(offer);
            }
        }
        catch(SQLException e){ System.err.println("SQLException: " + e.getMessage()); }
        return offers;
    }

    @Override
    public Offer getOfferById(String id) throws SQLException {
        String query = "SELECT * FROM offers WHERE id = ? ";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, id);

        ResultSet resultSet = stmt.executeQuery();
        if(resultSet.next()){
            Contract contract = contractRepository.getContractById(resultSet.getString("contractId"));
            return new Offer(
                    resultSet.getString("id"),
                    contract,
                    resultSet.getString("offerName"),
                    resultSet.getString("offerDescription"),
                    resultSet.getDate("startDate"),
                    resultSet.getDate("endDate"),
                    DiscountType.valueOf(resultSet.getString("discountType")),
                    resultSet.getDouble("discountValue"),
                    resultSet.getString("conditions"),
                    OfferStatus.valueOf(resultSet.getString("offerStatus"))
            );
        }
        else{
            System.out.println("no Offers with that id is available");
            return null;
        }
    }

    @Override
    public void addOffer(Offer offer) throws SQLException {
        String query = "INSERT INTO offers (id, contractid, offername, offerdescription, startdate, enddate, discounttype, discountvalue, conditions, offerstatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, offer.getId());
            stmt.setString(2, offer.getContract().getId());
            stmt.setString(3, offer.getOfferName());
            stmt.setString(4, offer.getDescription());
            stmt.setDate(5, new Date(offer.getStartDate().getTime()));
            stmt.setDate(6, new Date(offer.getEndDate().getTime()));
            stmt.setObject(7, offer.getDiscountType().name(), java.sql.Types.OTHER);
            stmt.setDouble(8, offer.getDiscountValue());
            stmt.setString(9, offer.getConditions());
            stmt.setObject(10, offer.getOfferStatus().name(), java.sql.Types.OTHER);

            stmt.executeUpdate();
        }
        catch(SQLException e){ System.err.println("SQLException: " + e.getMessage()); }
    }

    @Override
    public void updateOffer(Offer offer) throws SQLException {
        String query = "UPDATE offers SET offername = ?, offerdescription = ?, startdate = ?, enddate = ?, discounttype = ?, discountvalue = ?, conditions = ?, offerstatus = ?  WHERE id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, offer.getOfferName());
            stmt.setString(2, offer.getDescription());
            stmt.setDate(3, new Date(offer.getStartDate().getTime()));
            stmt.setDate(4, new Date(offer.getEndDate().getTime()));
            stmt.setObject(5, offer.getDiscountType().name(), java.sql.Types.OTHER);
            stmt.setDouble(6, offer.getDiscountValue());
            stmt.setString(7, offer.getConditions());
            stmt.setObject(8, offer.getOfferStatus().name(), java.sql.Types.OTHER);
            stmt.setString(9, offer.getId());

            stmt.executeUpdate();
        }
        catch(SQLException e){ System.err.println("SQLException: " + e.getMessage()); }
    }

    @Override
    public void deleteOffer(String id) throws SQLException {
        String query = "UPDATE offers SET deletedat = NOW() WHERE id = ? ";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
        catch(SQLException e){ System.err.println("SQLException: " + e.getMessage()); }
    }
}
