package com.szalay.opencourtwebapp;

public enum DataFiles {

    JSON_LIST_OF_COURTS("birosagok-listaja-20200801.json"),
    JSON_CASE_GROUPS("ugycsoportok-20200801.json")
    ;

    private final String filePath;

    DataFiles(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

}
