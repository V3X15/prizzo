package com.test.fabrick.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    
    public static <T> T deserializeJson(String json, Class<T> clazz) throws Exception {
        return objectMapper.readValue(json, clazz);
    }
}
