package com.musicplayer.modules.cloud;

import java.util.List;

public interface CloudProvider {
    /**
     * Lists audio files in the bucket with the given prefix.
     * @param bucket The bucket name.
     * @param prefix The folder prefix (optional).
     * @return List of file keys.
     */
    List<String> listFiles(String bucket, String prefix);

    /**
     * Generates a pre-signed URL for streaming the file.
     * @param bucket The bucket name.
     * @param key The file key.
     * @return The pre-signed URL string.
     */
    String generatePresignedUrl(String bucket, String key);
}
