package main.com.ecoMove.repositories.implementations;

import main.com.ecoMove.database.PgsqlConnection;
import main.com.ecoMove.enums.ContractStatus;
import main.com.ecoMove.models.Contract;
import main.com.ecoMove.models.Partner;
import main.com.ecoMove.repositories.contracts.ContractRepositoryInterface;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class ContractRepository implements ContractRepositoryInterface {

    public Connection connection;
    public PartnerRepository partnerRepository;
    public ContractRepository() throws SQLException, IOException {
        this.connection = PgsqlConnection.getInstance().getConnection();
        this.partnerRepository = new PartnerRepository();
    }


    @Override
    public ArrayList<Contract> getContracts() throws SQLException {
        ArrayList<Contract> contracts = new ArrayList<Contract>();
        String sqlQuery = "SELECT * FROM contracts WHERE deletedAt IS NULL;";

        try {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {

                Partner partner = partnerRepository.getPartnerById(resultSet.getString("partnerId"));
                Contract contract = new Contract(
                        resultSet.getString("id"),
                        partner,
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getDouble("specialRate"),
                        resultSet.getString("agreementConditions"),
                        resultSet.getBoolean("renewable"),
                        ContractStatus.valueOf(resultSet.getString("contractStatus"))
                );
                contracts.add(contract);
            }
        }
        catch(SQLException e){ System.err.println("SQLException: " + e.getMessage()); }
        return contracts;
    }

    @Override
    public Contract getContractById(String id) throws SQLException {
        String query = "SELECT * FROM contracts WHERE id = ? ";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, id);

        ResultSet res = stmt.executeQuery();
        if(res.next()){
            Partner partner = partnerRepository.getPartnerById(res.getString("partnerId"));
            return new Contract(
                    res.getString("id"),
                    partner,
                    res.getDate("startDate"),
                    res.getDate("endDate"),
                    res.getDouble("specialRate"),
                    res.getString("agreementConditions"),
                    res.getBoolean("renewable"),
                    ContractStatus.valueOf(res.getString("contractStatus"))
            );
        }
        else{
            System.out.println("no Contracts with that id is available");
            return null;
        }
    }

    @Override
    public void addContract(Contract contract) throws SQLException {
        String query = "INSERT INTO contracts (id, partnerid, startdate, enddate, specialrate, agreementconditions, renewable, contractstatus) VALUES(?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);


            stmt.setString(1, contract.getId());
            stmt.setString(2, contract.getPartner().getId());
            stmt.setDate(3, new Date(contract.getStartDate().getTime()));
            stmt.setDate(4, new Date(contract.getEndDate().getTime()));
            stmt.setDouble(5, contract.getSpecialRate());
            stmt.setString(6, contract.getAgreementConditions());
            stmt.setBoolean(7, contract.getRenewable());
            stmt.setObject(8, contract.getContractStatus().name(), java.sql.Types.OTHER);

            stmt.executeUpdate();
        }
        catch(SQLException e){ System.err.println("SQLException: " + e.getMessage()); }
    }

    @Override
    public void updateContract(Contract contract) throws SQLException {
        String query = "UPDATE contracts SET startDate = ?, enddate = ?, specialrate = ?, agreementconditions = ?, renewable = ?, contractstatus = ? WHERE id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setDate(1,  new Date(contract.getStartDate().getTime()));
            stmt.setDate(2,  new Date(contract.getEndDate().getTime()));
            stmt.setDouble(3, contract.getSpecialRate());
            stmt.setString(4, contract.getAgreementConditions());
            stmt.setBoolean(5, contract.getRenewable());
            stmt.setObject(6, contract.getContractStatus().name(), java.sql.Types.OTHER);
            stmt.setString(7, contract.getId());
            stmt.executeUpdate();
        }
        catch(SQLException e){ System.err.println("SQLException: " + e.getMessage()); }
    }

    @Override
    public void deleteContract(String id) throws SQLException {
        String query = "UPDATE contracts SET deletedat = NOW() WHERE id = ? ";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
        catch(SQLException e){ System.err.println("SQLException: " + e.getMessage()); }
    }
}
