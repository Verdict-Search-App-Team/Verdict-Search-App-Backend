package com.szalay.opencourtwebapp;

import net.minidev.json.JSONObject;

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

    public JSONObject toJSON() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("urlString", this.urlString);
        jsonObj.put("dateTime", this.dateTime);
        return jsonObj;
    }

    public String getUrlString() {
        return urlString;
    }

    public String getDateTime() {
        return dateTime;
    }

    public static List<DownloadResult> parseFromList (String filePath) {
        List<Object> objectList = IOUtils.parseJSONToList(filePath);
        List<DownloadResult> downloadResultsList =  null;
        for (Object obj :
                objectList) {
            downloadResultsList.add((DownloadResult) obj);
        }
        return downloadResultsList;
    }
}
