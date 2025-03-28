package com.augmentedcooking.Config.Converters;

import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.NonNull;

import com.augmentedcooking.Utils.impl.CUIDConverter;

import io.github.thibaultmeyer.cuid.CUID;

@WritingConverter
public class CUIDToBytesConverter implements Converter<CUID, Binary> {

    @Override
    public Binary convert(@NonNull CUID source) {
        return new Binary(CUIDConverter.cuidToBytes(source));
    }
}
