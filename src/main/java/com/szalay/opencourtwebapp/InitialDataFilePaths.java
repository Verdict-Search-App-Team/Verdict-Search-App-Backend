package com.szalay.opencourtwebapp;

public enum InitialDataFilePaths {

    JSON_LIST_OF_COURTS("birosagok-listaja-20200801.json"),
    JSON_CASE_GROUPS("ugycsoportok-20200801.json"),
    DECISIONS_FILESYSTEM_LOCATION("/media/greg/FD_BETA9SR2/opencourt/debreceni-itelotabla")
    ;

    private final String filePath;

    InitialDataFilePaths(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

}
