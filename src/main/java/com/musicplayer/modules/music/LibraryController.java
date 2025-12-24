package com.musicplayer.modules.music;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
public class LibraryController {

    private final MusicService musicService;

    @GetMapping
    public ResponseEntity<List<Track>> getLibrary() {
        return ResponseEntity.ok(musicService.getAllTracks());
    }

    @PostMapping("/sync")
    public ResponseEntity<String> syncLibrary() {
        musicService.syncLibrary();
        return ResponseEntity.ok("Sync Started");
    }
}
