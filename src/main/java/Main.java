import workers.Worker;

import workers.db.DBManager;

import java.util.ArrayList;


public class Main {


    public static void main(String[] args) {
        ArrayList<Object> myWorkers = null;
        Worker worker = new Worker(1, "Mark", 10, "Position1", 70000, "89102324232", "Voronezh");
        DBManager dbManager = new DBManager();
        try {
            dbManager.connect();
            dbManager.createTable(Worker.class);
            //dbManager.insertIntoDB(worker);
            myWorkers = dbManager.getAllFromDB(Worker.class);
            System.out.println(myWorkers.toString());
        } finally {
            dbManager.disconnect();
        }

    }


}
