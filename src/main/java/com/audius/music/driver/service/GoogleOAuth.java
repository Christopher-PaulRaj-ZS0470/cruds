package com.audius.music.driver.service;


import com.audius.music.core.utils.SqlExecutorService;
import com.audius.music.driver.controller.OauthController;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.audius.music.core.utils.Configs.*;

@Slf4j
@Service
/* class to demonstrate use of Drive files list API */
public class GoogleOAuth {
    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "Audius";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */

    private SqlExecutorService sqlExecutorService;
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES =
            Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);
//    private static final String CREDENTIALS_FILE_PATH = "/home/christopher.paulraj@zucisystems.com/java/credentials.json";
//    private static final Logger logger = LoggerFactory.getLogger(OauthController.class);

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    @Autowired
    public GoogleOAuth(SqlExecutorService sqlExecutorService) {
        this.sqlExecutorService = sqlExecutorService;
    }


    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.

        Object CREDENTIALS_FILE_PATH_RESULT = this.sqlExecutorService.executeSelectSingle("""
            SELECT value
            FROM config.credentials;
        """);
        String inputStreamJson = CREDENTIALS_FILE_PATH_RESULT.toString();

        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(new java.io.ByteArrayInputStream(inputStreamJson.getBytes())));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8088).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        return credential;
    }

    public void oAuthCall() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        List<File> mp3Files = new java.util.ArrayList<>();
        List<File> files = fetchMp3FilesRecursively(service, rootFolderId, mp3Files);

        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.println("Files:"+ file);

            }
        }
    }

    public List<File> fetchAllMp3FromFolder(String rootFolderId)
            throws IOException, GeneralSecurityException {

        final NetHttpTransport HTTP_TRANSPORT =
                GoogleNetHttpTransport.newTrustedTransport();

        Drive driveService =
                new Drive.Builder(
                        HTTP_TRANSPORT,
                        JSON_FACTORY,
                        getCredentials(HTTP_TRANSPORT)
                )
                        .setApplicationName(APPLICATION_NAME)
                        .build();

        List<File> mp3Files = new ArrayList<>();

        fetchMp3FilesRecursively(driveService, rootFolderId, mp3Files);

        log.info("Total MP3 files found: {}", mp3Files.size());

        mp3Files.forEach(f ->
                log.info("MP3 → name={}, id={}", f.getName(), f.getId())
        );

        return mp3Files;
    }

    /* -------------------- RECURSIVE WALK -------------------- */

    private List<File> fetchMp3FilesRecursively(
            Drive driveService,
            String folderId,
            List<File> mp3Files
    ) throws IOException {

        String query =
                "'" + folderId + "' in parents and trashed = false";

        FileList result =
                driveService.files().list()
                        .setPageSize(10)
                        .setQ(query)
                        .setFields("files(id, name, mimeType)")
                        .execute();

        for (File file : result.getFiles()) {

            // Folder → recurse
            if (FOLDER_MIME_TYPE.equals(file.getMimeType())) {
                fetchMp3FilesRecursively(
                        driveService,
                        file.getId(),
                        mp3Files
                );
            }
            // MP3 → collect
            else if (MP3_MIME_TYPE.equals(file.getMimeType())) {
                mp3Files.add(file);
            }
        }
        return mp3Files;
    }

}