package com.szalay.opencourtwebapp;

import java.io.File;
import java.util.*;

public class TextProcessor {

    //SPECIALISED TEXT PROCESSING METHODS FOR COURT DECISIONS

    private static Map<String, Object> courtNamesMap;


    static {
        courtNamesMap = IOUtils.parseJSONToMap(InitialDataFilePaths.JSON_LIST_OF_COURTS.getFilePath());
    }

    public static String prepareDecisionText(String rawText) {
        Map<String, String> replacementMap = new HashMap<>(Map.of(
                "õ", "ő",
                "Õ", "Ő",
                "û", "ű",
                "û".toUpperCase(), "Ű"
        ));
        String preparedText = rawText.replaceAll("[\n]{1,10}", "\n");
        for (String target :
                replacementMap.keySet()) {
            if (preparedText.contains(target)) {
                preparedText = preparedText.replaceAll(target, replacementMap.get(target));
            }
        }
        return Objects.requireNonNullElse(preparedText, "Error in prepareText method!");
    }

    public static String extractCaseNumber(String fileName) {
        String firstLetter = fileName.charAt(0) + "";
        fileName = fileName.replace(firstLetter, firstLetter.toUpperCase());
        fileName = fileName.replace(".rtf", "");
        return fileName;
    }

    public static String extractCourtName(String decisionText) {
        Map<String, Object> courtNamesMap = IOUtils.parseJSONToMap(InitialDataFilePaths.JSON_LIST_OF_COURTS.getFilePath());
        for (String line : decisionText.split("\n")) {
            for (String courtCode : courtNamesMap.keySet()) {
                List<String> courtNamesArray = (ArrayList<String>) courtNamesMap.get(courtCode);
                for (String courtName : courtNamesArray) {
                    if (!courtName.equals("") && line.toLowerCase().contains(courtName.toLowerCase())) {
                        return courtName;
                    }
                }
            }
        }
        return null;
    }

    public static String extractCaseType(String fileName) {
        Map<String, Object> caseGroupsMap = IOUtils.parseJSONToMap(InitialDataFilePaths.JSON_CASE_GROUPS.getFilePath());
        for (String caseCode : caseGroupsMap.keySet()) {
            if (!caseCode.equals("") && fileName.toLowerCase().contains(caseCode.toLowerCase())) {
                return (String) caseGroupsMap.get(caseCode);
            }
        }
        return null;
    }

    public static int[] extractDecisionDate(String decisionText) {
        int[] dateArray = new int[]{1900, 1, 1};
        String[] months = new String[]{"jan", "feb", "márc", "ápr", "máj", "jún", "júl", "aug", "szept", "okt", "nov", "dec"};

        int lastindex = decisionText.lastIndexOf("20");


        String dateSubstringPlain = decisionText.substring(lastindex);
        String dateSubstring = dateSubstringPlain.substring(0, dateSubstringPlain.indexOf("\n") - 1);
        String[] dateSubstringArray = dateSubstring.split("(?=20[0-9]{2}[.]?[ ]?[a-z]{3,20}[ ]?[a-z]{0,10}[ ]?[0-9]{2})");

        //YEAR
        try {
            dateArray[0] = Integer.parseInt(dateSubstringArray[0].substring(0, 4));
        } catch (Exception e) {
            System.out.println("Hiba az év olvasásakor");
        }

        //MONTH
        for (int i = 0; i < months.length; i++) {
            if (dateSubstringArray[0].toLowerCase().contains(months[i])) {
                dateArray[1] = i + 1;
                break;
            }
        }
        //DAY
        try {
            dateArray[2] = Integer.parseInt(dateSubstringArray[0].substring(dateSubstringArray[0].length() - 2));
        } catch (Exception e) {
            System.out.println("Hiba a nap olvasásakor");
        }
        return dateArray;

    }

    public static String extractProcedureYear(String fileName) {
        String[] splitLine = fileName.split("-");
        return splitLine[2];
    }

    public static String extractSubjectMatter(String decisonText) {
        int[] indexes = new int[]{
                decisonText.indexOf("iránt"),
                decisonText.indexOf("bűntet"),
                decisonText.indexOf("vétség")
        };
        int lowestIndex = Integer.MAX_VALUE;
        for (int i = 1; i < 2; i++) {
            if (indexes[i] < lowestIndex) {
                lowestIndex = indexes[i];
            }
        }
        if (lowestIndex == indexes[0]) {
            if (decisonText.contains("ellen ") && decisonText.indexOf("ellen ") < lowestIndex) {
                return decisonText.substring(decisonText.indexOf("ellen "), lowestIndex);
            } else if (decisonText.contains("szemben ") && decisonText.indexOf("szemben ") < lowestIndex) {
                return decisonText.substring(decisonText.indexOf("szemben "), lowestIndex);
            }
        } else if ((lowestIndex == indexes[1] || lowestIndex == indexes[2])
                && (lowestIndex >= 0)) {
            //uj valtozo legyen
            String decisionSubstring = decisonText.substring(0, lowestIndex);
            decisionSubstring = decisionSubstring.substring(decisionSubstring.lastIndexOf('A'), lowestIndex);
            decisionSubstring = decisionSubstring.replaceAll("A ", "");
            decisionSubstring = decisionSubstring.replaceAll("Az ", "");
            return decisionSubstring;
        }
        return "Could not determine tárgy";

    }

    public static String extractGrammaticalKeywords(String decisionText) {
        //To be implemented
        return null;
    }

    public static String reconstructSource(File decisionFile, String courtName, String procedureYear) {
        // MINTA:  https://ukp.birosag.hu/portal-frontend/stream/birosagKod/0001/hatarozatAzonosito/Gfv.30155_2009_5//
        String courtCode = "";
        String subject = decisionFile.getName().split("-")[0];
        subject = subject.substring(0, 1).toUpperCase() + subject.substring(1);
        for (String code : courtNamesMap.keySet()) {
            if (!((ArrayList<String>) courtNamesMap.get(code)).get(0).contains(courtName)) {
            } else {
                courtCode = code;
            }
        }
        return "https://ukp.birosag.hu/portal-frontend/stream/birosagKod/"
                + courtCode + "/hatarozatAzonosito/"
                + subject + "."
                + decisionFile.getName().split("-")[1]
                + "_"
                + procedureYear + "_"
                + decisionFile.getName().split("-")[decisionFile.getName().split("-").length - 1];
    }


}
