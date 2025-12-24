package com.musicplayer.modules.music;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String artist;
    private String album;
    private String filename;

    // Core for Cloud Streaming
    private String cloudPath; // Object Key (e.g. "music/song.mp3")
    private String cloudProvider; // "S3" or "SPACE_BYTE"
    private String bucket;
}
