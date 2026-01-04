package com.musicplayer.config;

import com.musicplayer.config.dto.PropertiesDto;
import com.musicplayer.config.repo.PropertiesRepo;
import com.musicplayer.config.entity.ConfigProperties;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


@SpringBootTest
public class PropertiesTest {

    @Autowired
    private PropertiesRepo properties;

    @Test
    public void propertiesTest() {
        Optional<ConfigProperties> result = properties.findPropertiesByVariable("spacebyte_base_url");
        System.out.println(result);
    }
}


