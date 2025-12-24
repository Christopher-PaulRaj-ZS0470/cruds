package com.musicplayer.modules.cloud;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudService {

    private final SpaceByteProvider spaceByteProvider;

    // In a real app, these come from the DB (Encrypted)
    // For Phase 2 MVP, we mock connecting to a Space Byte / S3 bucket.
    // User would POST these credentials via /api/cloud/link
    private String mockEndpoint = "https://s3.spacebyte.com"; // or minio
    private String mockAccessKey = "demo";
    private String mockSecretKey = "demo123";
    private String mockRegion = "us-east-1";
    private String mockBucket = "music-library";

    public void linkAccount(String provider, String accessKey, String secretKey, String endpoint) {
        log.info("Linking account for provider: {}", provider);
        // Save to DB (Skipped for now, using mock in-memory config)
        this.mockAccessKey = accessKey;
        this.mockSecretKey = secretKey;
        if (endpoint != null && !endpoint.isEmpty())
            this.mockEndpoint = endpoint;
    }

    public List<String> scanLibrary() {
        log.info("Scanning library from bucket: {}", mockBucket);
        try (S3Client s3 = spaceByteProvider.createClient(mockEndpoint, mockAccessKey, mockSecretKey, mockRegion)) {
            return spaceByteProvider.listFiles(s3, mockBucket, "");
        } catch (Exception e) {
            log.error("Failed to scan library", e);
            throw new RuntimeException("Cloud Sync Failed: " + e.getMessage());
        }
    }
}
