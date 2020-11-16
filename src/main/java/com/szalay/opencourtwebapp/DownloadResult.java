package com.szalay.opencourtwebapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DownloadResult {

    final String urlString;
    final String dateTime;

    public DownloadResult(String urlString) {
        this.urlString = urlString;
        this.dateTime = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(Calendar.getInstance().getTime());
    }

    public static List<DownloadResult> parseFromList(String filePath) {
        List<Object> objectList = IOUtils.parseJSONToList(filePath);
        List<DownloadResult> downloadResultsList = null;
        for (Object obj :
                objectList) {
            downloadResultsList.add((DownloadResult) obj);
        }
        return downloadResultsList;
    }

    public String getUrlString() {
        return urlString;
    }

}
