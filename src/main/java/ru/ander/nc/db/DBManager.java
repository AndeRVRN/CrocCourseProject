package ru.ander.nc.db;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.ander.nc.workers.Worker;

import java.sql.*;

public class DBManager {
    private static final Logger LOGGER = LogManager.getLogger(DBManager.class);

    private static Connection conn;
    private static Statement stmt;

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:workers.db");
            stmt = conn.createStatement();
            LOGGER.info("Connected to database");
        } catch (Exception e) {
            LOGGER.error("Problem with database connection: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        try {
            stmt.close();
        } catch (SQLException e) {
            LOGGER.error("Problem with statement close: " + e.getMessage());
            throw new RuntimeException(e);
        }
        try {
            conn.close();
        } catch (SQLException e) {
            LOGGER.error("Problem with connection close: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    String CREATE_WORKERS_QUERY =
            "CREATE TABLE IF NOT EXISTS workers ("
                    + "id       INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name     TEXT,"
                    + "position TEXT,"
                    + "age      INTEGER,"
                    + "salary   INTEGER,"
                    + "addInfoID TEXT    REFERENCES additional (id) ON DELETE CASCADE"
                    + ");";

    String CREATE_ADDITIONAL_QUERY =
            "CREATE TABLE IF NOT EXISTS additional ("
                    + "id        INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "telephone TEXT,"
                    + "address   TEXT"
                    + ");";


    public void createTable() {
        try {
            stmt.executeUpdate(CREATE_ADDITIONAL_QUERY);
            stmt.executeUpdate(CREATE_WORKERS_QUERY);
            LOGGER.info("Table created if not exists");
        } catch (SQLException e) {
            LOGGER.error("Problem with table creation: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private int getLatestIDFromTable(String tableName) throws SQLException {
        String getNewIDQuery = "SELECT MAX(id) FROM " + tableName;
        int newID = -1;
        ResultSet rs = stmt.executeQuery(getNewIDQuery);
        if (rs.next()) {
            newID = rs.getInt(1);
        }
        return newID;
    }

    public int findAvgForColumn(String columnName) {
        try {
            int avgSalary = 0;
            ResultSet rs = stmt.executeQuery("SELECT AVG(salary) from workers");
            if (rs.next()) {
                avgSalary = rs.getInt(1);
            }
            return avgSalary;
        } catch (SQLException e) {
            LOGGER.error("Problem with select avg by " + columnName + ": " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public double findAvgForColumnByPosition(String columnForAvg, String positionValue) {
        try {
            double avgSalary = 0;
            ResultSet rs = stmt.executeQuery("SELECT AVG(salary) from workers where position = '" + positionValue + "'");
            if (rs.next()) {
                avgSalary = rs.getInt(1);
            }
            return avgSalary;
        } catch (SQLException e) {
            LOGGER.error("Problem with select avg by " + columnForAvg + ": " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void insertIntoDB(Worker worker) {
        try {
            String insertQuery = "INSERT INTO additional (telephone, address) VALUES (?,?)";
            PreparedStatement ps = conn.prepareStatement(insertQuery);
            ps.setString(1, worker.getTelephone());
            ps.setString(2, worker.getAddress());
            ps.executeUpdate();

            int newAdditionalID = getLatestIDFromTable("additional");
            if (newAdditionalID != -1) {
                insertQuery = "INSERT INTO workers (name, position, age, salary, addInfoID) VALUES (?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(insertQuery);
                ps.setString(1, worker.getName());
                ps.setString(2, worker.getPosition());
                ps.setInt(3, worker.getAge());
                ps.setInt(4, worker.getSalary());
                ps.setInt(5, newAdditionalID);
                ps.executeUpdate();

                int newWorkerID = getLatestIDFromTable("workers");
                if (newWorkerID != -1) {
                    worker.setId(newWorkerID);
                }

            } else {
                LOGGER.error("Something is wrong with Worker additional info inserting");
                throw new RuntimeException("Something is wrong with Worker additional info inserting");
            }
        } catch (SQLException e) {
            LOGGER.error("Problem with prepared statement creation: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAllWorkersFromTable() {
        String getAllFromTableQuery = "SELECT w.id as workerID, w.name, w.position, w.age, w.salary, w.addInfoID, a.id as addID, a.telephone, a.address " +
                "FROM workers w, additional a where w.addInfoId = a.id";
        try {
            return stmt.executeQuery(getAllFromTableQuery);
        } catch (SQLException e) {
            LOGGER.error("Problem with object select from db: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
