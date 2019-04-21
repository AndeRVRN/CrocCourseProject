package ru.ander.nc.export;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.ander.nc.workers.Worker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ExportToFile {
    private static final Logger LOGGER = LogManager.getLogger(ExportToFile.class);

    public void export(String fileName, ArrayList<Worker> myWorkers) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            xmlMapper.writeValue(new File(fileName + ".xml"), myWorkers);

        } catch (IOException e) {
            LOGGER.error("Problem with write data in json file: " + e.getMessage());
        }
    }
}
