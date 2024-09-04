package main.com.ecoMove.repositories.contracts;

import main.com.ecoMove.models.Offer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OfferRepositoryInterface {

    public ArrayList<Offer> getOffers() throws SQLException;
    public Offer getOfferById(String id) throws SQLException;
    public void addOffer(Offer offer) throws SQLException;
    public void updateOffer(Offer offer) throws SQLException;
    public void deleteOffer(String id) throws SQLException;
}
