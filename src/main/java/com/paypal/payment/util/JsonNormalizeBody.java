package com.paypal.payment.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public class JsonNormalizeBody {
    public static String canonicalizeJson(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        Object json = mapper.readValue(jsonString, Object.class);
        return mapper.writeValueAsString(json);
    }
}
