package com.szalay.opencourtwebapp;

import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextProcessor {

    //GENERAL HELPER METHODS

    public static String readString(String filepath) {
        try {
            return Files.readString(Path.of(filepath));
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String readRtf(String filePath) {
        String result = null;
        File file = new File(filePath);
        try {
            DefaultStyledDocument styledDoc = new DefaultStyledDocument();
            InputStream is = new FileInputStream(file);
            new RTFEditorKit().read(is, styledDoc, 0);
            result = new String(styledDoc.getText(0, styledDoc.getLength()).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
        } catch (BadLocationException e) {
        }
        return result;
    }

    public static Map<String, Object> parseJSONToMap(String filePath) {
        JsonParser myParser = new BasicJsonParser();
        return myParser.parseMap(readString(filePath));
    }


    //SPECIALISED TEXT PROCESSING METHODS FOR COURT DECISIONS

    public static String prepareDecisionText(String rawText) {
        Map<String, String> replacementMap = new HashMap<>(Map.of(
                "õ", "ő",
                "Õ", "Ő",
//                "Õ".toLowerCase(), "ő",
//                "Õ", "Ő",
                "û", "ű",
                "û".toUpperCase(), "Ű"
        ));
        String preparedText = rawText.replaceAll("[\n]{1,10}", "\n");
//        System.out.println(preparedText);
        for (String target :
                replacementMap.keySet()) {
            if (preparedText.contains(target)) {
                preparedText = preparedText.replaceAll(target, replacementMap.get(target));
            }
        }
        if (preparedText != null) {
            return preparedText;
        } else return "Error in prepareText method!";
    }

    public static String extractCaseNumber(String fileName) {
        String firstLetter = fileName.charAt(0) + "";
        fileName = fileName.replace(firstLetter, firstLetter.toUpperCase());
        fileName = fileName.replace(".rtf", "");
        return fileName;
    }

    public static String extractCourtName(String decisionText) {
        Map<String, Object> courtNamesMap = parseJSONToMap(InitialDataFilePaths.JSON_LIST_OF_COURTS.getFilePath());
        for (String line : decisionText.split("\n")) {
            for (String courtCode : courtNamesMap.keySet()) {
                List<String> courtNamesArray = (ArrayList<String>)courtNamesMap.get(courtCode);
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
        Map<String, Object> caseGroupsMap = parseJSONToMap(InitialDataFilePaths.JSON_CASE_GROUPS.getFilePath());
        for (String caseCode : caseGroupsMap.keySet()) {
            if (!caseCode.equals("") && fileName.toLowerCase().contains(caseCode.toLowerCase())) {
                return (String) caseGroupsMap.get(caseCode);
            }
        }
        return null;
    }

    public static int[] extractDecisionDate(String decisionText) {
        int[] datumArray = new int[]{1900, 1, 1};
        String[] honapok = new String[]{"jan", "feb", "márc", "ápr", "máj", "jún", "júl", "aug", "szept", "okt", "nov", "dec"};

        int lastindex = decisionText.lastIndexOf("20");


        String dateSubstringPlain = decisionText.substring(lastindex);
        String dateSubstring = dateSubstringPlain.substring(0, dateSubstringPlain.indexOf("\n") - 1);
        String[] dateSubstringArray = dateSubstring.split("(?=20[0-9]{2}[.]?[ ]?[a-z]{3,20}[ ]?[a-z]{0,10}[ ]?[0-9]{2})");

        //ÉV
        try {
            datumArray[0] = Integer.parseInt(dateSubstringArray[0].substring(0, 4));
        } catch (Exception e) {
            System.out.println("Hiba az év olvasásakor");
        }

        //HÓNAP
        for (int i = 0; i < honapok.length; i++) {
            if (dateSubstringArray[0].toLowerCase().contains(honapok[i])) {
                datumArray[1] = i + 1;
                break;
            }
        }
        //NAP
        try {
            datumArray[2] = Integer.parseInt(dateSubstringArray[0].substring(dateSubstringArray[0].length() - 2));
        } catch (Exception e) {
            System.out.println("Hiba a nap olvasásakor");
        }
        return datumArray;

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
        } else if (lowestIndex == indexes[1] || lowestIndex == indexes[2]) {
            //uj valtozo legyen
            String hatarozatszovegSub = decisonText.substring(0, lowestIndex);
            hatarozatszovegSub = hatarozatszovegSub.substring(hatarozatszovegSub.lastIndexOf('A'), lowestIndex);
            hatarozatszovegSub = hatarozatszovegSub.replaceAll("A ", "");
            hatarozatszovegSub = hatarozatszovegSub.replaceAll("Az ", "");
            return hatarozatszovegSub;
        }
        return "Could not determine tárgy";

    }

    public static String extractGrammaticalKeywords(String decisionText) {
        //EGYELŐRE NEM MŰKÖDIK RENDESEN:
//        String kulcsSzavak = decisionText;
//        String[] hatarozatszovegSplit = decisionText.split(" ");
//        for (String szo :
//                hatarozatszovegSplit) {
//            if (gyakoriszavak.contains(szo.replaceAll(" ", ""))
//                    && !szo.equals("\n")){
//                kulcsSzavak = kulcsSzavak.replaceAll(szo, ";");
//            }
//        }
//        kulcsSzavak = kulcsSzavak.replaceAll("<br>", ";");
////        kulcsSzavak = kulcsSzavak.replaceAll("[a-z]{0,4}", ";");
//        kulcsSzavak = kulcsSzavak.replaceAll("[;]{2,10}", "");
//        return kulcsSzavak;
        return null;
    }


}
