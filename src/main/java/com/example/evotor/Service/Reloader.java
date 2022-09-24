package com.example.evotor.Service;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class Reloader extends Application {
    public String browser(String url) {

        getHostServices().showDocument(url);
        return "evotor";
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}

