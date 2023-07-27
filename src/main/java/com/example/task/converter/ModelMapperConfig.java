package com.example.task.converter;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class ModelMapperConfig {
    public ModelMapper modelMapper() {
        // Tạo object và cấu hình
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}
