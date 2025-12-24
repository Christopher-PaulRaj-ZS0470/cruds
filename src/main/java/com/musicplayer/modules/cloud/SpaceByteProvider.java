package com.musicplayer.modules.cloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SpaceByteProvider implements CloudProvider {

    // In a real app, these would be dynamic per user connection or stored in DB.
    // implementing as a factory or dynamic service would be better, but for now
    // we assume a single configured "Space Byte" endpoint for the demo/mvp or pass creds.
    // For this architecture, we will instantiate clients on the fly based on stored credentials,
    // but here we show the core logic.
    
    public S3Client createClient(String endpoint, String accessKey, String secretKey, String region) {
       return S3Client.builder()
               .endpointOverride(URI.create(endpoint))
               .region(Region.of(region)) // Space Byte/MinIO often use "us-east-1" or specific region
               .credentialsProvider(StaticCredentialsProvider.create(
                       AwsBasicCredentials.create(accessKey, secretKey)))
               .build();
    }
    
    public S3Presigner createPresigner(String endpoint, String accessKey, String secretKey, String region) {
        return S3Presigner.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    @Override
    public List<String> listFiles(String bucket, String prefix) {
        // TODO: This should take credentials as args or context.
        // For MVP, we will rely on methods that accept the client/credentials from the Service layer
        // or refactor this interface to accept a "CloudConnectionConfiguration".
        throw new UnsupportedOperationException("Use overloaded method with credentials");
    }

    public List<String> listFiles(S3Client s3, String bucket, String prefix) {
        ListObjectsV2Request req = ListObjectsV2Request.builder()
                .bucket(bucket)
                .prefix(prefix)
                .build();
        
        // Handling Only First Page for MVP
        ListObjectsV2Response res = s3.listObjectsV2(req);
        
        return res.contents().stream()
                .map(S3Object::key)
                .filter(key -> key.endsWith(".mp3") || key.endsWith(".flac") || key.endsWith(".m4a"))
                .collect(Collectors.toList());
    }

    @Override
    public String generatePresignedUrl(String bucket, String key) {
         throw new UnsupportedOperationException("Use overloaded method with credentials");
    }
    
    public String generatePresignedUrl(S3Presigner presigner, String bucket, String key) {
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(60))
                .getObjectRequest(b -> b.bucket(bucket).key(key))
                .build();
                
        return presigner.presignGetObject(presignRequest).url().toString();
    }
}
