package main.com.ecoMove.repositories.contracts;

import main.com.ecoMove.models.Contract;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ContractRepositoryInterface {

    public ArrayList<Contract> getContracts() throws SQLException;
    public Contract getContractById(String id) throws SQLException;
    public void addContract(Contract contract) throws SQLException;
    public void updateContract(Contract contract) throws SQLException;
    public void deleteContract(String id) throws SQLException;
}
