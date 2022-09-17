package com.example.evotor.Controllers;

import com.example.evotor.Service.Reloader;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.stereotype.Service;


@Service
public class GoogleAuthorizeUtil {

    public static Credential authorize() throws IOException, GeneralSecurityException {

        InputStream in = GoogleAuthorizeUtil.class.getResourceAsStream("/google-sheets-client-secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(GsonFactory.getDefaultInstance(), new InputStreamReader(in));
        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
                .Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(), clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline")
                .build();
        Credential credential = flow.loadCredential("User");
        if (credential == null) {
            LocalServerReceiver receiver = new LocalServerReceiver();
            String newRedirectUri = receiver.getRedirectUri();
            AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
            authorizationUrl.setRedirectUri(newRedirectUri);
            String url = authorizationUrl.build();

            Reloader reloader = new Reloader();
            reloader.browser(url);

            String code = receiver.waitForCode();
            TokenResponse response = flow.newTokenRequest(code).setRedirectUri(newRedirectUri).execute();
            credential = flow.createAndStoreCredential(response, "User");
            receiver.stop();

        }
        return credential;
    }


}
