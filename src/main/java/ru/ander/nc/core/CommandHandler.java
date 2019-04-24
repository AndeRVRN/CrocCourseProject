package ru.ander.nc.core;

import ru.ander.nc.export.ExportToDB;
import ru.ander.nc.export.ExportToFile;
import ru.ander.nc.workers.Worker;
import ru.ander.nc.workers.WorkerManager;

import java.util.ArrayList;
import java.util.Scanner;

public class CommandHandler {

    private static final String CMD_HELP = "help";
    private static final String CMD_EXIT = "exit";
    private static final String CMD_GET_ALL = "getAll";
    private static final String CMD_CREATE_WORKER = "createWorker";
    private static final String CMD_FIND_AVG_SALARY = "findAvgSalary";
    private static final String CMD_FIND_AVG_SALARY_BY_POSITION = "findAvgSalaryByPosition";
    private static final String CMD_FIND_WORKER_BY_TELEPHONE = "findWorkerByTelephone";
    private static final String CMD_EXPORT_TO_DB = "exportToDB";
    private static final String CMD_EXPORT_TO_FILE = "exportToFile";

    private WorkerManager workerManager = new WorkerManager();
    private ExportToDB etd = new ExportToDB();
    private ExportToFile etf = new ExportToFile();
    private Scanner scanner = new Scanner(System.in);
    public CommandHandler() {
        System.out.println("Welcome to Course Project done by Erik.");
        System.out.println("To get additional info type 'help' in console.");
    }

    public void requestCommandFromUser() {
        while (true) {
            System.out.print("Enter command: ");
            String cmd = scanner.nextLine();
            String[] s = cmd.split(" ");
            if (cmd.length() == 0 || s.length == 0) {
                System.out.println("Input a command please");
                continue;
            }
            defineCommand(s);

        }
    }

    private void printArrayList(ArrayList<Worker> arrayList) {
        for (Worker worker : arrayList) {
            System.out.println(worker.toString());
        }
    }

    public void defineCommand(String[] cmd) {
        switch (cmd[0]) {
            case CMD_EXIT:
                System.exit(0);
            case CMD_HELP:
                printHelp();
                break;
            case CMD_GET_ALL:
                if (cmd.length == 1) {
                    printArrayList(workerManager.getAllWorkers());
                } else {
                    System.out.println("This command doesn't have arguments");
                }
                break;
            case CMD_CREATE_WORKER:
                if (cmd.length == 7) {
                    Worker worker = new Worker(cmd[1], cmd[2], Integer.valueOf(cmd[3]), Integer.valueOf(cmd[4]), cmd[5], cmd[6]);
                    workerManager.createNewWorker(worker);
                } else {
                    System.out.println("Invalid input command. Example: 'createWorker (Name) (Position) (Age) (Salary) (Telephone) (Address)'");
                }
                break;
            case CMD_FIND_AVG_SALARY:
                if (cmd.length == 1) {
                    System.out.println("Average salary for workers is " + workerManager.getAvgSalary());
                } else {
                    System.out.println("This command doesn't have arguments");
                }
                break;
            case CMD_FIND_AVG_SALARY_BY_POSITION:
                if (cmd.length == 2) {
                    double avgSalary = workerManager.getAvgSalaryByPosition(cmd[1]);
                    if (avgSalary > 0) {
                        System.out.println("Average salary by position '" + cmd[1] + "' is " + avgSalary);
                    } else {
                        System.out.println("Average salary by position '" + cmd[1] + "' cannot be calculated");
                    }
                } else {
                    System.out.println("Invalid input command. Example: 'findAvgSalaryByPosition (Position)'");
                }
                break;
            case CMD_FIND_WORKER_BY_TELEPHONE:
                if (cmd.length == 2) {
                    Worker myWorker = workerManager.getWorkerByTelephone(cmd[1]);
                    if (myWorker != null) {
                        System.out.println("Worker by " + cmd[1] + " telephone: " + myWorker.toString());
                    } else {
                        System.out.println("No workers found by " + cmd[1] + " telephone");
                    }
                } else {
                    System.out.println("Invalid input command. Example: 'findWorkerByTelephone (Telephone)'");
                }
                break;
            case CMD_EXPORT_TO_DB:
                if (cmd.length == 2) {
                    etd.export(cmd[1], workerManager);
                } else {
                    System.out.println("Invalid input command. Example: 'exportToDB (JSONFileName)'");
                }
                break;
            case CMD_EXPORT_TO_FILE:
                if (cmd.length == 2) {
                    etf.export(cmd[1], workerManager.getAllWorkers());
                } else {
                    System.out.println("Invalid input command. Example: 'exportToFile (XMLFileName)'");
                }
                break;
        }
    }

    private void printHelp() {
        System.out.println("Available commands: ");
        System.out.println("getAll - show all workers;");
        System.out.println("createWorker (Name) (Position) (Age) (Salary) (Telephone) (Address) - create new worker;");
        System.out.println("findAvgSalary - calculate average salary from all workers;");
        System.out.println("findAvgSalaryByPosition (Position) - find average salary from workers filtrated by positions;");
        System.out.println("findWorkerByTelephone (Telephone) - find worker by telephone;");
        System.out.println("exportToDB (JSONFileName) - export JSON file to database;");
        System.out.println("exportToFile (XMLFileName) - export all database to xml file;");
        System.out.println("exit - close application.");

    }

    public void dispose() {
        workerManager.dispose();
    }
}
