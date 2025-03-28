package com.augmentedcooking.Config.Converters;

import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.NonNull;

import com.augmentedcooking.Utils.impl.CUIDConverter;

import io.github.thibaultmeyer.cuid.CUID;

@ReadingConverter
public class BytesToCUIDConverter implements Converter<Binary, CUID> {

    @Override
    public CUID convert(@NonNull Binary source) {
        return CUID.fromString(
                CUIDConverter.bytesToCuidString(
                        source.getData()));
    }
}
