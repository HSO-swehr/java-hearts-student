package de.hso.cardgame.util;

import java.util.logging.*;
import java.io.*;

public class Logging {
    static {
        try {
            // Try to load from class path first
            var url = Logging.class.getClassLoader().getResource("logging.properties");
            if (url != null) {
                // Resource found in classpath
                LogManager.getLogManager().readConfiguration(url.openStream());
                System.err.println("Successfully configured logging via URL: " + url);
            } else {
                // Fallback to look in the resources directory
                try {
                    File file = new File("resources/logging.properties");
                    if (file.exists()) {
                        FileInputStream stream = new FileInputStream(file);
                        LogManager.getLogManager().readConfiguration(stream);
                        System.err.println("Successfully configured logging via file: " + file.getAbsolutePath());
                    } else {
                        System.err.println("Warning: Could not find logging.properties file. Using default logging configuration.");
                    }
                } catch (IOException e) {
                    System.err.println("Warning: Could not load logging.properties file. Using default logging configuration.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error configuring logging: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Logger getLogger(String name) {
        return Logger.getLogger(name);
    }
}
