package com.textFileManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GospelsManager {

    // Initialize the Logger
    private static final Logger LOGGER = Logger.getLogger(GospelsManager.class.getName());

    public List<GospelInfo> getGospelsInfo() {
        List<GospelInfo> list = new ArrayList<>();

        // Attempt to get URL to the The_Gospels directory
        URL gospelsDirUrl = getClass().getClassLoader().getResource("The_Gospels");
        if (gospelsDirUrl == null) {
            LOGGER.severe("Directory 'The_Gospels' not found in classpath.");
            return list;
        }

        File gospelsDir;
        try {
            gospelsDir = new File(gospelsDirUrl.toURI());
        } catch (URISyntaxException e) {
            LOGGER.log(Level.SEVERE, "Invalid URI syntax for The_Gospels directory.", e);
            return list;
        }
        File[] files = gospelsDir.listFiles((_, name) -> name.toLowerCase().endsWith(".html"));

        if (files != null) {
            for (File file : files) {
                String firstLine = readFirstLineFromFile(file.getName());
                if (firstLine != null) {
                    // Remove <h1>...</h1> tags
                    String displayName = firstLine.replace("<h1>", "").replace("</h1>", "").trim();
                    list.add(new GospelInfo(file.getName(), displayName));
                }
            }
        } else {
            LOGGER.warning("No HTML files found in The_Gospels directory.");
        }

        return list;
    }


    /**
     * Reads the first line from a resource file by its name.
     */
    private String readFirstLineFromFile(String fileName) {
        String resourcePath = "The_Gospels/" + fileName;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                LOGGER.severe("Resource not found: " + resourcePath);
                return null;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                return reader.readLine();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading file: " + resourcePath, e);
            return null;
        }
    }

    public String getGospelContent(String fileName) {
        // Assumes the file is located in the The_Gospels folder in resources
        String resourcePath = "The_Gospels/" + fileName;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                LOGGER.severe("File not found: " + resourcePath);
                return null; // File not found
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading content from file: " + resourcePath, e);
            return null;
        }
    }
}
