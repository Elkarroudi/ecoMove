package main.com.ecoMove.models;

import main.com.ecoMove.enums.ContractStatus;

import java.util.ArrayList;
import java.util.Date;

public class Contract {

    private String id;
    private Partner partner;
    private Date startDate;
    private Date endDate;
    private double specialRate;
    private String agreementConditions;
    private Boolean renewable;
    private ContractStatus contractStatus;
    private ArrayList<Ticket> tickets = new ArrayList<>();
    private ArrayList<Offer> offers = new ArrayList<>();

    public Contract() {}
    public Contract (String id, Partner partner, Date startDate, Date endDate, Double specialRate, String agreementConditions, Boolean renewable, ContractStatus contractStatus) {
        this.id = id;
        this.partner = partner;
        this.startDate = startDate;
        this.endDate = endDate;
        this.specialRate = specialRate;
        this.agreementConditions = agreementConditions;
        this.renewable = renewable;
        this.contractStatus = contractStatus;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Partner getPartner() {
        return partner;
    }
    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getSpecialRate() {
        return specialRate;
    }
    public void setSpecialRate(Double specialRate) {
        this.specialRate = specialRate;
    }

    public String getAgreementConditions() {
        return agreementConditions;
    }
    public void setAgreementConditions(String agreementConditions) {
        this.agreementConditions = agreementConditions;
    }

    public Boolean getRenewable() {
        return renewable;
    }
    public void setRenewable(Boolean renewable) {
        this.renewable = renewable;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }
    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }
    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    public ArrayList<Offer> getOffers() {
        return offers;
    }
    public void setOffers(ArrayList<Offer> offers) {
        this.offers = offers;
    }
}
