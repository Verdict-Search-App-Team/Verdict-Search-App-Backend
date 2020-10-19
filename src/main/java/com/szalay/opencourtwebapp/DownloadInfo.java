package com.szalay.opencourtwebapp;

import org.springframework.boot.json.BasicJsonParser;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class DownloadInfo{

    private Map<String, Object> map;

    public DownloadInfo(String[] courts, int caseNumbersStart, int caseNumbersEnd, String[] caseGroups) {
        this.map.put("courts", courts);
        this.map.put("case-numbers-start", caseNumbersStart);
        this.map.put("case-numbers-end", caseNumbersEnd);
        this.map.put("caseGroups", caseGroups);
    }

    public static  Collection<Map<String, Object>> parseFromList (String filePath) {
        List<Object> parsedList= IOUtils.parseJSONToList(filePath);
        Collection<Map<String, Object>> downloadInfoMapCollection = new HashSet<>();
        for (Object o :
                parsedList) {
            System.out.println(o);
            System.out.println(o.getClass());
            try{
                downloadInfoMapCollection.add(new BasicJsonParser().parseMap((String) o));
                System.out.println("I'm on the ttry");
            } catch (Exception e){
                //e.printStackTrace();
                continue;
            }

            //System.out.println((String) o);
        }
        System.out.println("DownloadCollection: " + downloadInfoMapCollection);
        return downloadInfoMapCollection;
    }

    public Map<String, Object> getMap() {
        return map;
    }
}
