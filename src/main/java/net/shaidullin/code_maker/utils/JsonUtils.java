package net.shaidullin.code_maker.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;


public class JsonUtils {
    private static final Log logger = LogFactory.getLog(JsonUtils.class);

    private static ObjectMapper OBJECT_MAPPER;

    public static ObjectMapper getObjectMapper() {
        if (OBJECT_MAPPER != null) {
            return OBJECT_MAPPER;
        }

        ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.INDENT_OUTPUT, true);

        objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        OBJECT_MAPPER = objectMapper;

        return OBJECT_MAPPER;
    }

    public static <T> T readValue(InputStream value, Class<T> clz) {
        try {
            return getObjectMapper().readValue(value, clz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(byte[] value, Class<T> clz) {
        try {
            return getObjectMapper().readValue(value, clz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
