package export;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import workers.Worker;
import workers.WorkerManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExportToDB {
    private static final Logger LOGGER = LogManager.getLogger(ExportToDB.class);

    public void export(String fileName, WorkerManager workerManager) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            ArrayList<Worker> myWorkers = objectMapper.readValue(new File(fileName + ".json"), new TypeReference<List<Worker>>() {
            });
            for (Worker worker : myWorkers) {
                workerManager.createNewWorker(worker);
            }
            LOGGER.info("File '" + fileName + "' was exported to DB.");
        } catch (IOException e) {
            LOGGER.error("Problem with write data in json file: " + e.getMessage());
        }
    }
}
