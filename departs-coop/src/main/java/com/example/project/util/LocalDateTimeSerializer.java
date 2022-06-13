package com.example.project.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeSerializer
        extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime dateTime,
                          JsonGenerator generator,
                          SerializerProvider serializerProvider) throws IOException {
        generator.writeString(dateTime.toString());
    }
}
