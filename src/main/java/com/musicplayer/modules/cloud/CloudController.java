package com.musicplayer.modules.cloud;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cloud")
@RequiredArgsConstructor
public class CloudController {

    private final CloudService cloudService;

    @PostMapping("/link")
    public ResponseEntity<String> linkAccount(@RequestBody Map<String, String> config) {
        cloudService.linkAccount(
                config.get("provider"),
                config.get("accessKey"),
                config.get("secretKey"),
                config.get("endpoint"));
        return ResponseEntity.ok("Account Linked Successfully");
    }

    @PostMapping("/scan")
    public ResponseEntity<List<String>> scanLibrary() {
        return ResponseEntity.ok(cloudService.scanLibrary());
    }
}
