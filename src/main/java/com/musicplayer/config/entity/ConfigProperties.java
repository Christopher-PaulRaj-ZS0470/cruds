package com.musicplayer.config.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(schema = "config", name = "properties")
public class ConfigProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String variable;

    private String value;

    // Getters and setters are recommended
    public Long getId() {
        return id;
    }


}
