import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import export.ExportToDB;
import export.ExportToFile;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.*;
import workers.Worker;
import workers.WorkerManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExportTest {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private static WorkerManager workerManager;
    private static ExportToDB etd;
    private static ExportToFile etf;

    @BeforeClass
    public static void initTest() {
        workerManager = new WorkerManager();
        etd = new ExportToDB();
        etf = new ExportToFile();
    }

    @AfterClass
    public static void disposeTest() {
        workerManager.dispose();
    }

    @Test
    public void exportToDBTest() {
        String jsonFileName = "exportToDBTest";
        try {
            ArrayList<Worker> myWorkers = new ArrayList<>();

            myWorkers.add(new Worker("Kit", "Actor", 22, 35000, "89103124232", "Yopakl"));
            myWorkers.add(new Worker("Haris", "Director", 23, 70000, "89103124332", "Iporek"));
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(new File(jsonFileName + ".json"), myWorkers);

            etd.export(jsonFileName, workerManager);

        } catch (IOException e) {
            LOGGER.error("Problem with write data in json file: " + e.getMessage());
            Assert.fail("Problem with write data in json file: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e);
            Assert.fail("Error while export JSON file to DB.");
        } finally {
            File file = new File(jsonFileName + ".json");
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Test
    public void exportToFileTest() {
        File file = null;
        String xmlFileName = "exportToDBTest";
        try {
            etf.export(xmlFileName, workerManager.getAllWorkers());
            file = new File(xmlFileName + ".xml");
            if (!file.exists()) {
                Assert.fail("Xml file does not exists.");
            }
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            ArrayList<Worker> myWorkers = xmlMapper.readValue(new File(xmlFileName+".xml"), new TypeReference<List<Worker>>(){});
            if (myWorkers == null || myWorkers.size() == 0) {
                Assert.fail("File should not be null or empty.");
            }
            for (Worker worker: myWorkers) {
                LOGGER.info("Worker from XML in exportToFileText: " + worker.toString());
            }

        } catch (Exception e) {
            LOGGER.error(e);
            Assert.fail("Error while export DB to XML file.");
        } finally {
            file = new File(xmlFileName + ".xml");
            if (file.exists()) {
                file.delete();
            }
        }
    }

}
