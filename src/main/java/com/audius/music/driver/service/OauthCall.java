package com.audius.music.driver.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.audius.music.driver.service.GoogleOAuth;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j

@Service
public class OauthCall {

    private final GoogleOAuth googleOAuth;

    public OauthCall(GoogleOAuth googleOAuth) {
        this.googleOAuth = googleOAuth;
    }

    public void oauth() throws Exception {
        googleOAuth.oAuthCall();
    }
}

