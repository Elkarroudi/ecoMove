package main.com.ecoMove.models;

import main.com.ecoMove.enums.PartnerStatus;
import main.com.ecoMove.enums.TransportType;

import java.util.ArrayList;
import java.util.Date;

public class Partner {

    private String id;
    private String companyName;
    private String commercialContact;
    private TransportType transportType;
    private String geographicalArea;
    private String specialConditions;
    private PartnerStatus partnerStatus;
    private Date creationDate;
    private ArrayList<Contract> contracts = new ArrayList<>();


    public Partner(String id, String companyName, String commercialContact, TransportType transportType, String geographicalArea, String specialConditions, PartnerStatus partnerStatus, Date creationDate) {
        this.id = id;
        this.companyName = companyName;
        this.commercialContact = commercialContact;
        this.transportType = transportType;
        this.geographicalArea = geographicalArea;
        this.specialConditions = specialConditions;
        this.partnerStatus = partnerStatus;
        this.creationDate = creationDate;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCommercialContact() {
        return commercialContact;
    }
    public void setCommercialContact(String commercialContact) {
        this.commercialContact = commercialContact;
    }

    public TransportType getTransportType() {
        return transportType;
    }
    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public String getGeographicalArea() {
        return geographicalArea;
    }
    public void setGeographicalArea(String geographicalArea) {
        this.geographicalArea = geographicalArea;
    }

    public String getSpecialConditions() {
        return specialConditions;
    }
    public void setSpecialConditions(String specialConditions) {
        this.specialConditions = specialConditions;
    }

    public PartnerStatus getPartnerStatus() {
        return partnerStatus;
    }
    public void setPartnerStatus(PartnerStatus partnerStatus) {
        this.partnerStatus = partnerStatus;
    }

    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public ArrayList<Contract> getContracts() {
        return contracts;
    }
    public void setContracts(ArrayList<Contract> contracts) {
        this.contracts = contracts;
    }

}
