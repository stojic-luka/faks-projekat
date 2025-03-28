package com.augmentedcooking.Config.Serialization;

import java.io.IOException;

import com.augmentedcooking.Utils.impl.CUIDConverter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import io.github.thibaultmeyer.cuid.CUID;

public class CUIDDeserializer extends JsonDeserializer<CUID> {

    @Override
    public CUID deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return CUID.fromString(
                CUIDConverter.bytesToCuidString(
                        p.getBinaryValue()));
    }
}
