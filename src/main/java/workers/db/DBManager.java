package workers.db;

import workers.Worker;
import workers.annotations.*;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class DBManager {
    private static Connection conn;
    private static Statement stmt;

    public void connect() {

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:workers.db");
            stmt = conn.createStatement();
        } catch (Exception e) {
            throw new RuntimeException("Problem with database conn: " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Problem with statement close: " + e.getMessage());
        }
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Problem with connection close: " + e.getMessage());
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


    public void createTable(/*Class cls*/) {
        try {
            stmt.executeUpdate(CREATE_ADDITIONAL_QUERY);
            stmt.executeUpdate(CREATE_WORKERS_QUERY);
        } catch (SQLException e) {
            throw new RuntimeException("Problem with table creation: " + e.getMessage());
        }

   /*     if (!cls.isAnnotationPresent(WorkerTable.class)) {
            throw new RuntimeException("@WorkerTable is missed");
        }

        HashMap<Class, String> converter = new HashMap<Class, String>();
        converter.put(String.class, "TEXT");
        converter.put(int.class, "INTEGER");

        StringBuilder sbMain = new StringBuilder();
        StringBuilder sbAdditional = new StringBuilder();

        sbMain.append("CREATE TABLE IF NOT EXISTS ");
        sbMain.append(((WorkerTable) cls.getAnnotation(WorkerTable.class)).tableName());
        sbMain.append(" (");

        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(WorkerField.class)) {
                sbMain.append(f.getName()).append(" ");
                sbMain.append(converter.get(f.getType())).append(" ");
                if (f.isAnnotationPresent(WorkerFieldPrimary.class)) {
                    sbMain.append(" PRIMARY KEY AUTOINCREMENT");
                }
                if (f.isAnnotationPresent(WorkerFieldReference.class)) {
                    sbMain.append(" REFERENCES ").append(f.getAnnotation(WorkerFieldReference.class).referencedTabletitle());
                    sbMain.append("(").append(f.getAnnotation(WorkerFieldReference.class).referencedFieldTitle()).append(")");
                }
                if (f.isAnnotationPresent(WorkerFieldCascadeDelete.class)) {
                    sbMain.append(" ON DELETE CASCADE");
                }
                sbMain.append(", ");
            }
        }
        sbMain.setLength(sbMain.length() - 2);
        sbMain.append(");");
        try {
            stmt.executeUpdate(sbMain.toString());
        } catch (SQLException e) {
            throw new RuntimeException("Problem with table creation: " + e.getMessage());
        }*/
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
       }
       catch (SQLException e) {
           throw new RuntimeException("Problem with select avg by "+columnName+": " + e.getMessage());
       }
    }

    public double findAvgForColumnByPosition(String columnForAvg, String positionValue) {
        try {
            double avgSalary = 0;
            ResultSet rs = stmt.executeQuery("SELECT AVG(salary) from workers where position = '"+positionValue+"'");
            if (rs.next()) {
                avgSalary = rs.getInt(1);
            }
            return avgSalary;
        }
        catch (SQLException e) {
            throw new RuntimeException("Problem with select avg by "+columnForAvg+": " + e.getMessage());
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
                throw new RuntimeException("Something is wrong with Worker additional info inserting");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Problem with prepared statement creation: " + e.getMessage());
        }



     /*   Class cls = object.getClass();
        if (!cls.isAnnotationPresent(WorkerTable.class)){
            throw new RuntimeException("@WorkerTable is missed");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO  ");
        sb.append(((WorkerTable) cls.getAnnotation(WorkerTable.class)).tableName());
        sb.append(" (");

        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(WorkerField.class)) {
                sb.append(f.getName()).append(", ");
            }
        }
        sb.setLength(sb.length() - 2);
        sb.append(") VALUES (");
        for (Field f : fields) {
            if (f.isAnnotationPresent(WorkerField.class)) {
                sb.append("?, ");
            }
        }
        sb.setLength(sb.length() - 2);
        sb.append(");");

        System.out.println(sb.toString());
        try {
            PreparedStatement ps = conn.prepareStatement(sb.toString());
            int counter = 1;
            for (Field f : fields) {
                if (f.isAnnotationPresent(WorkerField.class)) {
                    f.setAccessible(true);
                    ps.setObject(counter, f.get(object));
                    counter++;
                }
            }
            ps.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException("Problem with prepared statement creation: " + e.getMessage());
        }*/
    }

    public ResultSet getAllWorkersFromTable() {
        String getAllFromTableQuery = "SELECT w.id as workerID, w.name, w.position, w.age, w.salary, w.addInfoID, a.id as addID, a.telephone, a.address " +
                                      "FROM workers w, additional a where w.addInfoId = a.id";
        try {
            return stmt.executeQuery(getAllFromTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException("Problem with object select from db: " + e.getMessage());
        }
      /*  if (!cls.isAnnotationPresent(WorkerTable.class)){
            throw new RuntimeException("@WorkerTable is missed");
        }
        ArrayList<Object> objectArrayList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(((WorkerTable) cls.getAnnotation(WorkerTable.class)).tableName());
        try {
            ResultSet rs = stmt.executeQuery(sb.toString());
            while (rs.next()) {
                int counter = 1;
                Object newObject = cls.newInstance();
                Field[] fields = cls.getDeclaredFields();
                for (Field f : fields) {
                    if (f.isAnnotationPresent(WorkerField.class)) {
                        f.setAccessible(true);
                        if (int.class.equals(f.getType())) {
                            f.set(newObject, rs.getInt(counter));
                        } else if (String.class.equals(f.getType())) {
                            f.set(newObject, rs.getString(counter));
                        }
                        counter++;
                    }
                }
                objectArrayList.add(newObject);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Problem with object select from db: " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Problem with setting value in object field : " + e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return objectArrayList;*/
    }
}
