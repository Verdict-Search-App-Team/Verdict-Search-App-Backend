package com.szalay.opencourtwebapp;

import org.springframework.boot.json.BasicJsonParser;

import java.util.*;

public class DownloadInfo {

    public DownloadInfo(String[] courts, int caseNumbersStart, int caseNumbersEnd, String[] caseGroups, Map<String, Object> map) {
        Objects.requireNonNull(map).put("courts", courts);
        map.put("case-numbers-start", caseNumbersStart);
        map.put("case-numbers-end", caseNumbersEnd);
        map.put("caseGroups", caseGroups);
    }

    public static Collection<Map<String, Object>> parseFromList(String filePath) {
        List<Object> parsedList = IOUtils.parseJSONToList(filePath);
        Collection<Map<String, Object>> downloadInfoMapCollection = new HashSet<>();
        for (Object o :
                parsedList) {
            System.out.println(o);
            System.out.println(o.getClass());
            try {
                downloadInfoMapCollection.add(new BasicJsonParser().parseMap((String) o));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return downloadInfoMapCollection;
    }

}
