package com.audius.music.driver.service;


import com.audius.music.MusicApplication;
import com.audius.music.core.utils.Configs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

import static com.audius.music.core.utils.Configs.TOKENS_DIRECTORY_PATH;

@Service
public class DeleteTokenFolder {
    private static final Logger logger = LoggerFactory.getLogger(MusicApplication.class);

    public Integer deleteTokensFolder() throws Exception {
        Integer statusCode = null;
        File tokenDir = new File(TOKENS_DIRECTORY_PATH);
        if (tokenDir.exists()) {
            logger.info("token directory exists");
            Path directory = Paths.get(TOKENS_DIRECTORY_PATH);
            try (Stream<Path> pathStream = Files.walk(directory)) {
                pathStream.sorted(Comparator.reverseOrder()) // Sort in reverse order (bottom-up)
                        .forEach(path -> {
                            try {
                                Files.delete(path); // Delete file or empty directory
                            } catch (IOException e) {
                                logger.error("Failed to delete: " + path + " - " + e.getMessage());
                            }
                        });
            }
            statusCode = 200;
        }else {
            statusCode = 404;
            logger.info("Np directory exists");
        }
        return statusCode;
    }
}