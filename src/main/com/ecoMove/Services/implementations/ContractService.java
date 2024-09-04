package main.com.ecoMove.Services.implementations;

import main.com.ecoMove.Services.contracts.ContractServiceInterface;
import main.com.ecoMove.enums.ContractStatus;
import main.com.ecoMove.enums.PartnerStatus;
import main.com.ecoMove.enums.TransportType;
import main.com.ecoMove.models.Contract;
import main.com.ecoMove.models.Partner;
import main.com.ecoMove.repositories.implementations.ContractRepository;
import main.com.ecoMove.repositories.implementations.PartnerRepository;
import main.com.ecoMove.utils.EnumHelper;
import main.com.ecoMove.utils.Helper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Scanner;

public class ContractService implements ContractServiceInterface {

    private PartnerRepository partnerRepository;
    private ContractRepository contractRepository;
    private Scanner scanner;
    public ContractService() throws SQLException, IOException {
        this.partnerRepository = new PartnerRepository();
        this.contractRepository = new ContractRepository();
        this.scanner = new Scanner(System.in);
    }


    @Override
    public void getContracts() throws SQLException {
        ArrayList<Contract> contracts = contractRepository.getContracts();

        if (contracts.isEmpty()) {
            System.out.println("No contracts found");
            return;
        }

        String leftAlignFormat = "| %-36s | %-36s | %-15s | %-15s | %-15s | %-25s | %-10s | %-15s |%n";

        System.out.format("+--------------------------------------+--------------------------------------+-----------------+-----------------+-----------------+---------------------------+------------+-----------------+%n");
        System.out.format("| Contract ID                          | Partner ID                           | Start Date      | End Date        | Special Rate    | Agreement Conditions      | Renewable  | Status          |%n");
        System.out.format("+--------------------------------------+--------------------------------------+-----------------+-----------------+-----------------+---------------------------+------------+-----------------+%n");

        for(Contract contract : contracts) {
            System.out.format(
                    leftAlignFormat,
                    contract.getId(),
                    contract.getPartner().getId(),
                    contract.getStartDate(),
                    contract.getEndDate(),
                    contract.getSpecialRate(),
                    contract.getAgreementConditions(),
                    contract.getRenewable(),
                    contract.getContractStatus()
            );
        }
        System.out.format("+--------------------------------------+--------------------------------------+-----------------+-----------------+-----------------+---------------------------+------------+-----------------+%n");

    }

    @Override
    public void addContract() throws SQLException {

        System.out.println("Enter partner ID:");
        String partnerId = scanner.nextLine();
        Partner partner = partnerRepository.getPartnerById(partnerId);

        if (partner == null) {
            System.out.println("No partner found with that ID.");
            return;
        }

        System.out.println("Enter contract start date (yyyy-mm-dd) :");
        String startDateInput = scanner.nextLine();
        Date startDate = Date.valueOf(startDateInput);  // Convert string to java.sql.Date

        System.out.println("Enter contract end date (yyyy-mm-dd) :");
        String endDateInput = scanner.nextLine();
        Date endDate = Date.valueOf(endDateInput);  // Convert string to java.sql.Date

        System.out.println("Enter special rate:");
        double specialRate = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        System.out.println("Enter agreement conditions:");
        String agreementConditions = scanner.nextLine();

        System.out.println("Is the contract renewable? (true/false):");
        boolean renewable = scanner.nextBoolean();
        scanner.nextLine(); // consume newline

        ContractStatus contractStatus = EnumHelper.handleContractStatusEnums(scanner);

        Contract contract = new Contract(
                Helper.generateUniqueId(),
                partner,
                startDate,
                endDate,
                specialRate,
                agreementConditions,
                renewable,
                contractStatus
        );

        try{
            contractRepository.addContract(contract);
            System.out.println(" contract Was Added successfully");
        }
        catch(SQLException e){ System.err.println("SQLException: " + e.getMessage()); }
    }

    @Override
    public void updateContract() throws SQLException {
        this.getContracts();
        while (true) {
            System.out.println("Please enter the id of the contract you would like to update");
            String id = scanner.nextLine();

            try {
                Contract contract = contractRepository.getContractById(id);

                if (contract != null) {

                    System.out.println("Updating contract ------------------------------------------------------------------------------------------");

                    System.out.println("Current Start Date: " + contract.getStartDate());
                    System.out.println("Enter new Start Date (yyyy-mm-dd) (or press Enter to keep the current one):");
                    String startDateInput = scanner.nextLine();
                    Date startDate = Date.valueOf(startDateInput);
                    if (!startDateInput.isEmpty()) {
                        startDate = Date.valueOf(startDateInput);
                    }

                    // Update end date if needed
                    System.out.println("Current End Date: " + contract.getEndDate());
                    System.out.println("Enter new End Date (yyyy-mm-dd) (or press Enter to keep the current one):");
                    String endDateInput = scanner.nextLine();
                    Date endDate = Date.valueOf(endDateInput);
                    if (!endDateInput.isEmpty()) {
                        endDate = Date.valueOf(endDateInput);
                    }

                    // Update special rate if needed
                    System.out.println("Current Special Rate: " + contract.getSpecialRate());
                    System.out.println("Enter new Special Rate (or press Enter to keep the current one):");
                    String specialRateInput = scanner.nextLine();
                    double specialRate = contract.getSpecialRate();
                    if (!specialRateInput.isEmpty()) {
                        specialRate = Double.parseDouble(specialRateInput);
                    }

                    // Update agreement conditions if needed
                    System.out.println("Current Agreement Conditions: " + contract.getAgreementConditions());
                    System.out.println("Enter new Agreement Conditions (or press Enter to keep the current ones):");
                    String agreementConditions = scanner.nextLine();
                    if (agreementConditions.isEmpty()) {
                        agreementConditions = contract.getAgreementConditions();
                    }

                    // Update renewable status if needed
                    System.out.println("Current Renewable Status: " + contract.getRenewable());
                    System.out.println("Is the contract renewable? (true/false) (or press Enter to keep the current one):");
                    String renewableInput = scanner.nextLine();
                    boolean renewable = contract.getRenewable();
                    if (!renewableInput.isEmpty()) {
                        renewable = Boolean.parseBoolean(renewableInput);
                    }

                    ContractStatus contractStatus = EnumHelper.handleContractStatusEnumsUpdate(scanner, contract.getContractStatus());
                    Partner partner = partnerRepository.getPartnerById(contract.getPartner().getId());

                    Contract contractToUpdate = new Contract(
                            id,
                            partner,
                            startDate,
                            endDate,
                            specialRate,
                            agreementConditions,
                            renewable,
                            contractStatus
                    );

                    contractRepository.updateContract(contractToUpdate);
                    System.out.println("contract was updated successfully!");
                    break;
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    @Override
    public void deleteContract() throws SQLException {
        this.getContracts();
        while (true) {
            System.out.println("Please enter the id of the contracts you would like to delete :");
            String id = scanner.nextLine();

            try {
                Contract contract = contractRepository.getContractById(id);
                if (contract != null) {
                    contractRepository.deleteContract(id);
                    System.out.println("Contract was deleted successfully!");
                    break;
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
