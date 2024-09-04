package main.com.ecoMove.Services.implementations;

import main.com.ecoMove.Services.contracts.PartnerServiceInterface;
import main.com.ecoMove.enums.PartnerStatus;
import main.com.ecoMove.enums.TransportType;
import main.com.ecoMove.models.Partner;
import main.com.ecoMove.repositories.implementations.PartnerRepository;
import main.com.ecoMove.utils.EnumHelper;
import main.com.ecoMove.utils.Helper;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class PartnerService implements PartnerServiceInterface {

    private PartnerRepository partnerRepository;
    private Scanner scanner;
    public PartnerService() throws SQLException, IOException {
        this.partnerRepository = new PartnerRepository();
        this.scanner = new Scanner(System.in);
    }


    @Override
    public void getPartners() throws SQLException {
        ArrayList<Partner> partners = partnerRepository.getPartners();

        if (partners.isEmpty()) {
            System.out.println("No partners found");
            return;
        }

        String leftAlignFormat = "| %-12s | %-20s | %-20s | %-18s | %-15s | %-25s | %-18s | %-20s |%n";

        System.out.format("+--------------------------------------+----------------------+----------------------+--------------------+-----------------+---------------------------+--------------------+----------------------+%n");
        System.out.format("| Partner ID                           | Company Name         | Commercial Contact   | Transportation Type| Geographic Zone | Special Conditions        | Partnership Status | Creation Date         |%n");
        System.out.format("+--------------------------------------+----------------------+----------------------+--------------------+-----------------+---------------------------+--------------------+----------------------+%n");

        for(Partner partner : partners){
            System.out.format(
                    leftAlignFormat,
                    partner.getId(),
                    partner.getCompanyName(),
                    partner.getCommercialContact(),
                    partner.getTransportType(),
                    partner.getGeographicalArea(),
                    partner.getSpecialConditions(),
                    partner.getPartnerStatus(),
                    partner.getCreationDate()
            );
        }
        System.out.format("+--------------------------------------+----------------------+----------------------+--------------------+-----------------+---------------------------+--------------------+----------------------+%n");

    }

    @Override
    public void addPartner() throws SQLException {

        System.out.println("Enter company name :");
        String companyName = scanner.nextLine();

        System.out.println("Enter Commercial contact :");
        String commercialContact = scanner.nextLine();

        TransportType transportationType = EnumHelper.handleTransportationEnums(scanner);
        scanner.nextLine();

        System.out.println("Enter Geographic Zone :");
        String geographicZone = scanner.nextLine();

        System.out.println("Enter Special Conditions :");
        String specialConditions = scanner.nextLine();

        PartnerStatus partnershipStatus = EnumHelper.handlePartnerStatusEnums(scanner);
        Timestamp creationDate = new Timestamp(new Date().getTime());

        Partner partner = new Partner(
                Helper.generateUniqueId(),
                companyName,
                commercialContact,
                transportationType,
                geographicZone,
                specialConditions,
                partnershipStatus,
                creationDate
        );

        try{
            partnerRepository.addPartner(partner);
            System.out.println(partner.getCompanyName() +" Was Added successfully");
        }
        catch(SQLException e){ System.err.println("SQLException: " + e.getMessage()); }
    }

    @Override
    public void updatePartner() throws SQLException {
        this.getPartners();
        while (true) {
            System.out.println("Please enter the id of the partner you would like to update");
            String id = scanner.nextLine();

            try {
                Partner partner = partnerRepository.getPartnerById(id);

                if (partner != null) {

                    System.out.println("Updating Partner "+ partner.getCompanyName() + "-------------------------------------------------------------------");

                    System.out.println("Current Company Name : " + partner.getCompanyName());
                    System.out.println("Enter new value Or leave blank field if you don't want to update !");
                    String companyName = scanner.nextLine();
                    if(companyName.isEmpty()) companyName = partner.getCompanyName();


                    System.out.println("Current Commercial Contact : " + partner.getCommercialContact());
                    System.out.println("Enter new value Or leave blank field if you don't want to update !");
                    String commercialContact = scanner.nextLine();
                    if(commercialContact.isEmpty()) commercialContact = partner.getCommercialContact();

                    TransportType transportationType = EnumHelper.handleTransportationEnumsUpdate(scanner, partner.getTransportType());

                    System.out.println("Current Geographic Zone : " + partner.getGeographicalArea());
                    System.out.println("Enter new value Or leave blank field if you don't want to update !");
                    String geographiqueZone = scanner.nextLine();
                    if(geographiqueZone.isEmpty()) geographiqueZone = partner.getGeographicalArea();

                    System.out.println("Current special Conditions : " + partner.getSpecialConditions());
                    System.out.println("Enter new value Or leave blank field if you don't want to update !");
                    String specialConditions = scanner.nextLine();
                    if(specialConditions.isEmpty()) specialConditions = partner.getSpecialConditions();

                    PartnerStatus partnershipStatus = EnumHelper.handlePartnerStatusEnumsUpdate(scanner, partner.getPartnerStatus());

                    Partner partnerToUpdate = new Partner(
                            partner.getId(),
                            companyName,
                            commercialContact,
                            transportationType,
                            geographiqueZone,
                            specialConditions,
                            partnershipStatus,
                            partner.getCreationDate()
                    );

                    partnerRepository.updatePartner(partnerToUpdate);
                    System.out.println("Partner " + partner.getCompanyName() + " updated successfully!");
                    break;
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    @Override
    public void deletePartner() throws SQLException {
        this.getPartners();
        while (true) {
            System.out.println("Please enter the id of the partner you would like to delete :");
            String id = scanner.nextLine();

            try {
                Partner partner = partnerRepository.getPartnerById(id);
                if (partner != null) {
                    partnerRepository.deletePartner(id);
                    System.out.println("Partner " + partner.getCompanyName() + " deleted successfully!");
                    break;
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

}
