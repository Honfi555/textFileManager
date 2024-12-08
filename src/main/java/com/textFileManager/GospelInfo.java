package com.textFileManager;

public class GospelInfo {
    private final String fileName;
    private final String displayName;

    public GospelInfo(String fileName, String displayName) {
        this.fileName = fileName;
        this.displayName = displayName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

