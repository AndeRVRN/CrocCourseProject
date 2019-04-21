import ru.ander.nc.export.ExportToDB;
import ru.ander.nc.export.ExportToFile;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.ander.nc.workers.Worker;
import ru.ander.nc.workers.WorkerManager;

import java.util.ArrayList;

public class GetDataFromDBTest {
    private static final Logger LOGGER = LogManager.getLogger(GetDataFromDBTest.class);

    private static WorkerManager workerManager = new WorkerManager();

    @BeforeClass
    public static void initTest() {
        workerManager = new WorkerManager();
    }

    @AfterClass
    public static void disposeTest() {
        workerManager.dispose();
    }

    @Test
    public void getAllWorkerTest() {
        try {
            ArrayList<Worker> myWorkers = workerManager.getAllWorkers();
            if (myWorkers == null || myWorkers.size() == 0) {
                Assert.fail("Workers list should not be null or empty.");
            }
        } catch (Exception e) {
            LOGGER.error(e);
            Assert.fail("Error while getting all ru.ander.nc.workers from DB test.");
        }
    }

    @Test
    public void getAvgSalaryTest() {
        try {
            double avgSalary = workerManager.getAvgSalary();
            LOGGER.info("Average salary = " + avgSalary);
        } catch (Exception e) {
            LOGGER.error(e);
            Assert.fail("Error while getting average salary from ru.ander.nc.workers.");
        }
    }

    @Test
    public void getAvgSalaryByPositionTest() {
        String position = "";
        try {
            position = "Accountant";
            double avgSalary = workerManager.getAvgSalaryByPosition(position);
            LOGGER.info("Average salary by '" + position + "' position = " + avgSalary);

            position = "SoftwareDeveloper";
            avgSalary = workerManager.getAvgSalaryByPosition(position);
            LOGGER.info("Average salary by '" + position + "' position = " + avgSalary);
        } catch (Exception e) {
            LOGGER.error(e);
            Assert.fail("Error while getting average salary from ru.ander.nc.workers by position = " + position + ".");
        }
    }

    @Test
    public void getWorkerByTelephoneTest() {
        try {
            Worker worker = workerManager.getWorkerByTelephone("89102846320");
            LOGGER.info("Worker by telephone = " + worker.toString());
        } catch (Exception e) {
            LOGGER.error(e);
            Assert.fail("Error while getting worker by telephone.");
        }
    }

}
