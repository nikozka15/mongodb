package com.nikozka.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.nikozka.app.ApplicationRunner.runApp;
public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {
        log.info("Start of application");
        runApp();
        log.info("End of application");
    }
}
