package export;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import workers.Worker;
import workers.db.DBManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ExportToFile {
    private static final Logger LOGGER = LogManager.getLogger(ExportToFile.class);

    public ExportToFile(ArrayList<Worker> myWorkers) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

           // for (Worker worker : myWorkers) {
                objectMapper.writeValue(new File("worker.json"), myWorkers);
          //  }
        } catch (IOException e) {
            LOGGER.error("Problem with write data in json file: " + e.getMessage());
        }
    }
}
