package ru.ander.nc.workers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.ander.nc.db.DBManager;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WorkerManager {
    private static final Logger LOGGER = LogManager.getLogger(WorkerManager.class);

    protected DBManager dbManager = new DBManager();

    protected ArrayList<Worker> myWorkers = new ArrayList<>();

    protected double avgSalaryForAllWorkers = 0;


    public WorkerManager() {
        dbManager.connect();
        dbManager.createTable();
        // load all users to cache
        getAllWorkers();


    }

    public void createNewWorker(Worker worker) {
        dbManager.insertIntoDB(worker);
        myWorkers.add(worker);
    }

    public double getAvgSalary() {
        if (avgSalaryForAllWorkers == 0) {
            avgSalaryForAllWorkers = dbManager.findAvgForColumn("salary");
            LOGGER.info("New average salary by worker = " + avgSalaryForAllWorkers);
        }
        return avgSalaryForAllWorkers;
    }

    public double getAvgSalaryByPosition(String position) {
        double avgSalary = dbManager.findAvgForColumnByPosition("salary", position);
        LOGGER.info("Average salary for '" + position + "' position equals " + avgSalary);
        return avgSalary;
    }

    public Worker getWorkerByTelephone(String telephone) {
        for (Worker curWorker : myWorkers) {
            if (curWorker.getTelephone() != null && curWorker.getTelephone().equals(telephone)) {
                LOGGER.info("Worker by telephone = " + curWorker.toString());
                return curWorker;
            }
        }
        return null;
    }

    public ArrayList<Worker> getAllWorkers() {
        if (myWorkers.isEmpty()) {
            myWorkers = dbManager.getAllWorkersFromTable();
        }
        return myWorkers;
    }

    public void dispose() {
        dbManager.disconnect();
        LOGGER.info("Connection to DB is closed");
    }
}
