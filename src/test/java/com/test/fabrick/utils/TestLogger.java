package com.test.fabrick.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TestLogger {
    
    private static Logger logger = null;
    private static ArrayNode logArray = null;
    private static final String LOG_FILE_PREFIX = "log";
    private static final String LOG_FILE_EXTENSION = ".txt";
    private static String logDirectory = "target/logs"; 

    private final ObjectMapper objectMapper = new ObjectMapper();

    public TestLogger(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
        logArray = objectMapper.createArrayNode();
    }

    public static void setLogDirectory(String path) {
        logDirectory = path;
    }

    private void logToJson(String level, String message) {
        ObjectNode logEntry = objectMapper.createObjectNode();
        logEntry.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        logEntry.put("level", level);
        logEntry.put("message", message);

        logArray.add(logEntry);

        try {
            saveLogsToFile();
        } catch (IOException e) {
            logger.error("Errore durante la scrittura dei log su file", e);
        }
    }

    private void saveLogsToFile() throws IOException {
        File directory = new File(logDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        String logFileName = LOG_FILE_PREFIX + " - " + timestamp + LOG_FILE_EXTENSION;
        String logFilePath = Paths.get(logDirectory, logFileName).toString();

        try (FileWriter file = new FileWriter(logFilePath)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, logArray);
        } catch (IOException e) {
            logger.error("Errore durante la scrittura dei log su file", e);
            throw e;
        }

        logArray = objectMapper.createArrayNode();
        logger.info("File Locale: " + logFileName + " creato con successo");
    }

    public void info(String message) {
        logger.info(message);
        logToJson("INFO", message);
    }
    
    public void warn(String message) {
        logger.warn(message);
        logToJson("WARN", message);
    }
    
    public void error(String message) {
        logger.error(message);
        logToJson("ERROR", message);
    }
    
    public void debug(String message) {
        logger.debug(message);
        logToJson("DEBUG", message);
    }
}
