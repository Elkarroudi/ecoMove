package main.com.ecoMove.models;

import main.com.ecoMove.enums.DiscountType;
import main.com.ecoMove.enums.OfferStatus;

import java.util.Date;

public class Offer {

    private String id;
    private Contract contract;
    private String offerName;
    private String offerDescription;
    private Date startDate;
    private Date endDate;
    private DiscountType discountType;
    private double discountValue;
    private String conditions;
    private OfferStatus offerStatus;


    public Offer(String id, Contract contract, String offerName, String offerDescription, Date startDate, Date endDate, DiscountType discountType, double discountValue, String conditions, OfferStatus offerStatus) {
        this.id = id;
        this.contract = contract;
        this.offerName = offerName;
        this.offerDescription = offerDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.conditions = conditions;
        this.offerStatus = offerStatus;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Contract getContract() {
        return contract;
    }
    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getOfferName() {
        return offerName;
    }
    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getDescription() {
        return offerDescription;
    }
    public void setDescription(String description) {
        this.offerDescription = description;
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

    public DiscountType getDiscountType() {
        return discountType;
    }
    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public double getDiscountValue() {
        return discountValue;
    }
    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public String getConditions() {
        return conditions;
    }
    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }
    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }
}
