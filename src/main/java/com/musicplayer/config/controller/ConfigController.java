package com.musicplayer.config.controller;

import com.musicplayer.config.entity.ConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.musicplayer.config.service.ConfigService;

import java.util.Optional;


@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("configProperties")
public class ConfigController {

    private ConfigService configService;

    @GetMapping("/get/properties/{variable}")
    public Optional<ConfigProperties> getConfigPropertiesByVariable(
            @PathVariable("variable") String variable) {
        return configService.getConfigProperties(variable);

    }
}
