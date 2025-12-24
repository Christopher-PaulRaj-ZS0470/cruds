package com.musicplayer.modules.music;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TrackRepository extends JpaRepository<Track, Long> {
    Optional<Track> findByCloudPath(String cloudPath);
}
