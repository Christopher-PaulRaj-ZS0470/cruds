package com.audius.music.driver.controller;

import com.audius.music.driver.service.DeleteTokenFolder;
import com.audius.music.driver.service.OauthCall;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/oauth")
public class OauthController {
    private final OauthCall oauthCall;
    private final DeleteTokenFolder deleteTokenFolder;

    public OauthController(OauthCall oauthCall, DeleteTokenFolder deleteTokenFolder) {
        this.oauthCall = oauthCall;
        this.deleteTokenFolder = deleteTokenFolder;
    }

    @PostMapping("/authentication/oauth")
    public String oAuthIntialiaze() throws Exception{
        oauthCall.oauth();
        return "OAuth initialized";
    }


    @PostMapping("/delete/tokensDir")
    public Integer removeTokenDir() throws Exception{
        final Integer statusCode =  deleteTokenFolder.deleteTokensFolder();
        return statusCode;
    }
}
