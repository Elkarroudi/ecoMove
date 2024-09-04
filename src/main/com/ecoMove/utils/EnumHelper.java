package main.com.ecoMove.utils;

import main.com.ecoMove.enums.ContractStatus;
import main.com.ecoMove.enums.PartnerStatus;
import main.com.ecoMove.enums.TransportType;
import main.com.ecoMove.models.Contract;

import java.util.InputMismatchException;
import java.util.Scanner;

public class EnumHelper {


    public static TransportType handleTransportationEnums (Scanner scanner) {
        while (true) {
            System.out.println("Please select the Transportation Type : \n");
            System.out.println("1. Train / 2. Bus / 3. Taxi");

            try{
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        return TransportType.TRAIN;
                    case 2 :
                        return TransportType.BUS;
                    case 3 :
                        return TransportType.TAXI;
                    default:
                        System.out.println("INVALID CHOICE !");
                        break;
                }
            }
            catch(InputMismatchException e){
                System.out.println("invalid input , please give a number");
                scanner.nextLine();
            }
        }
    }

    public static TransportType handleTransportationEnumsUpdate (Scanner scanner, TransportType OldValue) {
        while (true) {
            System.out.println("Please select the Transportation Type : \n");
            System.out.println("0.To Remain Old Value / 1. Train / 2. Bus / 3. Taxi");

            try{
                int choice = scanner.nextInt();

                switch (choice) {
                    case 0:
                        return OldValue;
                    case 1:
                        return TransportType.TRAIN;
                    case 2 :
                        return TransportType.BUS;
                    case 3 :
                        return TransportType.TAXI;
                    default:
                        System.out.println("INVALID CHOICE !");
                        break;
                }
            }
            catch(InputMismatchException e){
                System.out.println("invalid input , please give a number");
                scanner.nextLine();
            }
        }
    }

    public static PartnerStatus handlePartnerStatusEnums (Scanner scanner) {
        while (true) {
            System.out.println("Please select the Transportation Type : \n");
            System.out.println("1. Active / 2. Suspended / 3. Expired");

            try{
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        return PartnerStatus.ACTIVE;
                    case 2 :
                        return PartnerStatus.SUSPENDED;
                    case 3 :
                        return PartnerStatus.EXPIRED;
                    default:
                        System.out.println("INVALID CHOICE !");
                        break;
                }
            }
            catch(InputMismatchException e){
                System.out.println("invalid input , please give a number");
                scanner.nextLine();
            }
        }
    }

    public static PartnerStatus handlePartnerStatusEnumsUpdate (Scanner scanner, PartnerStatus OldValue) {
        while (true) {
            System.out.println("Please select the Transportation Type : \n");
            System.out.println("0.To Remain Old Value / 1. Active / 2. Suspended / 3. Expired");

            try{
                int choice = scanner.nextInt();

                switch (choice) {
                    case 0:
                        return OldValue;
                    case 1:
                        return PartnerStatus.ACTIVE;
                    case 2 :
                        return PartnerStatus.SUSPENDED;
                    case 3 :
                        return PartnerStatus.EXPIRED;
                    default:
                        System.out.println("INVALID CHOICE !");
                        break;
                }
            }
            catch(InputMismatchException e){
                System.out.println("invalid input , please give a number");
                scanner.nextLine();
            }
        }
    }

    public static ContractStatus handleContractStatusEnums (Scanner scanner) {
        while (true) {
            System.out.println("Please select the Contract Status : \n");
            System.out.println("1. Ongoing / 2. Terminated / 3. Suspended");

            try{
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        return ContractStatus.ONGOING;
                    case 2 :
                        return ContractStatus.TERMINATED;
                    case 3 :
                        return ContractStatus.SUSPENDED;
                    default:
                        System.out.println("INVALID CHOICE !");
                        break;
                }
            }
            catch(InputMismatchException e){
                System.out.println("invalid input , please give a number");
                scanner.nextLine();
            }
        }
    }

    public static ContractStatus handleContractStatusEnumsUpdate (Scanner scanner, ContractStatus OldValue) {
        while (true) {
            System.out.println("Please select the Contract Status : \n");
            System.out.println("0.To Remain Old Value  / 1. Ongoing / 2. Terminated / 3. Suspended");

            try{
                int choice = scanner.nextInt();

                switch (choice) {
                    case 0:
                        return OldValue;
                    case 1:
                        return ContractStatus.ONGOING;
                    case 2 :
                        return ContractStatus.TERMINATED;
                    case 3 :
                        return ContractStatus.SUSPENDED;
                    default:
                        System.out.println("INVALID CHOICE !");
                        break;
                }
            }
            catch(InputMismatchException e){
                System.out.println("invalid input , please give a number");
                scanner.nextLine();
            }
        }
    }

}
