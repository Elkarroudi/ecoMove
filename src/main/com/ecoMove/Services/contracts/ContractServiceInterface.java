package main.com.ecoMove.Services.contracts;

import main.com.ecoMove.models.Contract;

import java.sql.SQLException;

public interface ContractServiceInterface {

    public void getContracts() throws SQLException;
    public void addContract() throws SQLException;
    public void updateContract() throws SQLException;
    public void deleteContract() throws SQLException;
}
