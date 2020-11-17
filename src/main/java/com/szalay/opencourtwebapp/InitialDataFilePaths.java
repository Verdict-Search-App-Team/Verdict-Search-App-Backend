package com.szalay.opencourtwebapp;

public enum InitialDataFilePaths {

    JSON_LIST_OF_COURTS("src/main/resources/court-list-20200801.json"),
    JSON_CASE_GROUPS("src/main/resources/casegroups-20200801.json"),
    //for testing: /media/greg/FD_BETA9SR2/opencourt
    DECISIONS_FILESYSTEM_LOCATION("src/main/resources/decisions"),
    JSON_COURTS_CASENUMBERS_CASEGROUPS("src/main/resources/courts-casenumbers-casegroups.json");

    String filePath;

    InitialDataFilePaths(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
