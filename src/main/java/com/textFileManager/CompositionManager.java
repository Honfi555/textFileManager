package com.textFileManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompositionManager {
    private static final Logger LOGGER = Logger.getLogger(CompositionManager.class.getName());

    /**
     * Возвращает карту: Имя группы -> Список произведений (fileName, displayName)
     */
    public Map<String, List<CompositionInfo>> getAllGroupsAndCompositions() {
        Map<String, List<CompositionInfo>> map = new LinkedHashMap<>();

        URL rootUrl = getClass().getClassLoader().getResource("");
        if (rootUrl == null) {
            LOGGER.severe("Resources root not found.");
            return map;
        }

        File rootDir;
        try {
            rootDir = new File(rootUrl.toURI());
        } catch (URISyntaxException e) {
            LOGGER.log(Level.SEVERE, "Invalid URI for resource root.", e);
            return map;
        }

        // Перебираем все директории (группы)
        File[] dirs = rootDir.listFiles(File::isDirectory);
        if (dirs == null) return map;

        for (File dir : dirs) {
            String groupName = dir.getName();

            // Можно при необходимости пропускать папки, которые не являются группами.
            // Например, если есть META-INF, WEB-INF или icons
            if (groupName.equals("META-INF") || groupName.equals("WEB-INF") || groupName.equals("icons")) {
                continue;
            }

            List<CompositionInfo> compositions = new ArrayList<>();
            File[] files = dir.listFiles((_, name) -> name.toLowerCase().endsWith(".html"));
            if (files != null) {
                for (File file : files) {
                    String relativePath = groupName + "/" + file.getName();
                    String firstLine = readFirstLine(relativePath);
                    if (firstLine != null) {
                        String displayName = firstLine.replace("<h1>", "").replace("</h1>", "").trim();
                        compositions.add(new CompositionInfo(file.getName(), displayName));
                    }
                }
            }

            if (!compositions.isEmpty()) {
                map.put(groupName, compositions);
            }
        }

        return map;
    }

    /**
     * Читает первую строку из указанного ресурса.
     */
    private String readFirstLine(String resourcePath) {
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

    /**
     * Возвращает содержимое файла для указанной группы и имени файла.
     */
    public String getCompositionContent(String groupName, String fileName) {
        String resourcePath = groupName + "/" + fileName;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                LOGGER.severe("File not found: " + resourcePath);
                return null;
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
