package com.test.fabrick.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogger {
	
	private static Logger logger = null;
    private static JSONArray logArray = new JSONArray();
    private static final String LOG_FILE_PREFIX = "log";
    private static final String LOG_FILE_EXTENSION = ".txt";
    private static String logDirectory = "target/logs"; 

    public TestLogger(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    public static void setLogDirectory(String path) {
        logDirectory = path;
    }

    private void logToJson(String level, String message) throws JSONException {
        JSONObject logEntry = new JSONObject();
        logEntry.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        logEntry.put("level", level);
        logEntry.put("message", message);
        
        logArray.put(logEntry);
        saveLogsToFile();
    }

    private void saveLogsToFile() throws JSONException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        String logFileName = LOG_FILE_PREFIX + " - " + timestamp + LOG_FILE_EXTENSION;
        String logFilePath = Paths.get(logDirectory, logFileName).toString();
        
        System.err.println(logFilePath);
        
        try (FileWriter file = new FileWriter(logFilePath)) {
            file.write(logArray.toString(4)); 
            file.flush();
        } catch (IOException e) {
            logger.error("Errore durante la scrittura dei log su file", e);
        }
    }

    public void info(String message) throws JSONException {
        logger.info(message);
        logToJson("INFO", message);
    }
    
    public void warn(String message) throws JSONException {
        logger.warn(message);
        logToJson("WARN", message);
    }
    
    public void error(String message) throws JSONException {
        logger.error(message);
        logToJson("ERROR", message);
    }
    
    public void debug(String message) throws JSONException {
        logger.debug(message);
        logToJson("DEBUG", message);
    }
}