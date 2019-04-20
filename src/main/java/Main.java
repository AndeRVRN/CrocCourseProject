import workers.Worker;

import workers.WorkerManager;

import java.util.ArrayList;


public class Main {

    static WorkerManager workerManager = new WorkerManager();

    public static void main(String[] args) {
        try {
            ArrayList<Worker> myWorkers = null;
            Worker worker = new Worker("Mark", "Position1", 10, 70000, "89102324232", "Voronezh");
            //workerManager.createNewWorker(worker);
            myWorkers = workerManager.getAllWorkers();
            for (Worker curWorker : myWorkers) {
                System.out.println(curWorker.toString());
            }
            System.out.println("avg = " + workerManager.getAvgSalary());
            System.out.println("avg by pos = " + workerManager.getAvgSalaryByPosition("Position1"));
            System.out.println("worker by telephone = " + workerManager.getWorkerByTelephone("89102324235").toString());
        } finally {
            workerManager.dispose();
        }


    }


}
