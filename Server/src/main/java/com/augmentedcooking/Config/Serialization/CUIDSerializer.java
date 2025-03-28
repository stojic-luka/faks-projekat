package com.augmentedcooking.Config.Serialization;

import java.io.IOException;

import com.augmentedcooking.Utils.impl.CUIDConverter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.github.thibaultmeyer.cuid.CUID;

public class CUIDSerializer extends JsonSerializer<CUID> {

    @Override
    public void serialize(CUID value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null)
            throw new IllegalArgumentException("Invalid CUID value!");

        byte[] bytes = CUIDConverter.cuidToBytes(value);
        gen.writeBinary(bytes);
    }
}
