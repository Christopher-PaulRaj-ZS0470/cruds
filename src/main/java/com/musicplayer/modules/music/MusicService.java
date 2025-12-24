package com.musicplayer.modules.music;

import com.musicplayer.modules.cloud.CloudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MusicService {

    private final TrackRepository trackRepository;
    private final CloudService cloudService;

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    @Transactional
    public void syncLibrary() {
        log.info("Starting Library Sync...");
        List<String> files = cloudService.scanLibrary();

        for (String fileKey : files) {
            // Simple logic: If track doesn't exist by path, create it.
            if (trackRepository.findByCloudPath(fileKey).isEmpty()) {
                String[] parts = fileKey.split("/");
                String filename = parts[parts.length - 1];

                Track track = Track.builder()
                        .cloudPath(fileKey)
                        .title(filename) // Basic metadata extraction
                        .artist("Unknown Artist")
                        .cloudProvider("SPACE_BYTE") // Hardcoded for MVP
                        .bucket("music-library")
                        .filename(filename)
                        .build();

                trackRepository.save(track);
                log.info("Indexed new track: {}", filename);
            }
        }
        log.info("Library Sync Completed.");
    }
}
