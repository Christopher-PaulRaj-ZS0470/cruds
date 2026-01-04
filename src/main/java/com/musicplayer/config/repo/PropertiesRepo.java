package com.musicplayer.config.repo;

import com.musicplayer.config.entity.ConfigProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Properties;

@Repository
public interface PropertiesRepo extends JpaRepository<ConfigProperties, Long> {
    Optional<ConfigProperties> findPropertiesByVariable(String variable);

}
