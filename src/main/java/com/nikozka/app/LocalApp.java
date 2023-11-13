package com.nikozka.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalApp {
    private static final Logger log = LoggerFactory.getLogger(LocalApp.class);
    public static void main(String[] args) {
        log.info("Start of Local application");
        LocalApplicationRunner.runApp();
        log.info("End of Local application");
    }
}
