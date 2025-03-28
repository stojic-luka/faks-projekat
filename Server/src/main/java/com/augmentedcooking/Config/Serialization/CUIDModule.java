package com.augmentedcooking.Config.Serialization;

import com.fasterxml.jackson.databind.module.SimpleModule;

import io.github.thibaultmeyer.cuid.CUID;

public class CUIDModule extends SimpleModule {

    public CUIDModule() {
        addSerializer(CUID.class, new CUIDSerializer());
        addDeserializer(CUID.class, new CUIDDeserializer());
    }
}
