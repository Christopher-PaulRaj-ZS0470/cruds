package com.musicplayer.config.service;

import com.musicplayer.config.entity.ConfigProperties;
import com.musicplayer.config.repo.PropertiesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfigService {
    private PropertiesRepo properties;


    public Optional<ConfigProperties> getConfigProperties(String variable) {
        Optional<ConfigProperties> result = properties.findPropertiesByVariable(variable);
        return result;
    }
}
