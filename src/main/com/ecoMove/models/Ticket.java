package main.com.ecoMove.models;

import main.com.ecoMove.enums.TicketStatus;
import main.com.ecoMove.enums.TransportType;

import java.sql.Timestamp;

public class Ticket {

    private String id;
    private Contract contract;
    private TransportType transportType;
    private double purchasePrice;
    private double salePrice;
    private Timestamp saleDate;
    private TicketStatus ticketStatus;


    public Ticket (String id, Contract contract, TransportType transportType, double purchasePrice, double salePrice, Timestamp saleDate, TicketStatus ticketStatus) {
        this.id = id;
        this.contract = contract;
        this.transportType = transportType;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.saleDate = saleDate;
        this.ticketStatus = ticketStatus;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id; }

    public Contract getContract() {
        return contract;
    }
    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public TransportType getTransportType() {
        return transportType;
    }
    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }
    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSalePrice() {
        return salePrice;
    }
    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public Timestamp getSaleDate() {
        return saleDate;
    }
    public void setSaleDate(Timestamp saleDate) {
        this.saleDate = saleDate;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }
    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
}
