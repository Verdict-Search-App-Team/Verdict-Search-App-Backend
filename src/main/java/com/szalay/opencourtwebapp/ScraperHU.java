package com.szalay.opencourtwebapp;

import net.minidev.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.szalay.opencourtwebapp.InitialDataFilePaths.DECISIONS_FILESYSTEM_LOCATION;
import static com.szalay.opencourtwebapp.InitialDataFilePaths.JSON_COURTS_CASENUMBERS_CASEGROUPS;


public final class ScraperHU {

    private static final JSONArray successfulDownloadsInfo = new JSONArray();
    private static final JSONArray failedDownloadsInfo = new JSONArray();
    private static final int currentYear = Integer.parseInt(new SimpleDateFormat("yyyy")
            .format(Calendar.getInstance().getTime()));
    private static final int earliestYear = 2005;
    // File numbers are usually between 1-20
    private static final int highestFileNo = 20;
    private static List<DownloadResult> previousSuccessfulDownloadsInfo;
    private static List<DownloadResult> previousFailedDownloadsInfo;
    private static Collection<Map<String, Object>> downloadInfos;
    private static Map<String, Object> courtNamesMap;

    static {
        try {
            downloadInfos = DownloadInfo.parseFromList(JSON_COURTS_CASENUMBERS_CASEGROUPS.getFilePath());
            courtNamesMap = IOUtils.parseJSONToMap(InitialDataFilePaths.JSON_LIST_OF_COURTS.getFilePath());
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        try {
            String successfulDownloadsFilepath = DECISIONS_FILESYSTEM_LOCATION.getFilePath() + "/" + "successful-downloads-info.json";
            ensureFileExistence(successfulDownloadsFilepath);
            previousSuccessfulDownloadsInfo = DownloadResult.parseFromList(successfulDownloadsFilepath);
        } catch (Exception e) {
            System.err.println("could not parse " + DECISIONS_FILESYSTEM_LOCATION.getFilePath()
                    + "/" + "successful-downloads-info.json");
        }
        try {
            String failedDownloadsFilepath = DECISIONS_FILESYSTEM_LOCATION.getFilePath() + "/" + "failed-downloads-info.json";
            ensureFileExistence(failedDownloadsFilepath);
            previousFailedDownloadsInfo = DownloadResult.parseFromList(failedDownloadsFilepath);
        } catch (Exception e) {
            System.err.println("could not parse " + DECISIONS_FILESYSTEM_LOCATION.getFilePath()
                    + "/" + "failed-downloads-info.json");
        }
    }

    /*
    The following lists only contain the Register Numbers and Case Groups suitable for scraping.
    Thus there are decisions which will not be within the scope below and have to be downloaded/uploaded manually.
     */

    public static void ensureFileExistence(String filepath) throws IOException {
        File failedDownloadsFile = new File(filepath);
        if(failedDownloadsFile.createNewFile()){
            System.out.println("File " + failedDownloadsFile.getName() + " didn't exist, but we successfully created it");
        } else {
            System.out.println("File already exists, no problem");
        }
    }

    public static void start() {
        // https://ukp.birosag.hu/portal-frontend/stream/birosagKod/0001/hatarozatAzonosito/Gfv.30155_2009_5//

        // Every download info unit
        for (Map<String, Object> downloadInfo : downloadInfos) {
            // Every case number
            for (long caseNumber = (long) downloadInfo.get("case-numbers-start");
                 caseNumber < (long) downloadInfo.get("case-numbers-end"); caseNumber++) {
                // Every case group
                for (String caseGroup : (ArrayList<String>) downloadInfo.get("caseGroups")) {
                    // Every court
                    for (String courtCode : courtNamesMap.keySet()) {
                        // Every name of the court
                        for (String courtName : (ArrayList<String>) courtNamesMap.get(courtCode)) {
                            for (String courtType : (ArrayList<String>) downloadInfo.get("courts")) {
                                // if the court matches the download info unit
                                if (courtName.contains(courtType)) {
                                    // Every file number
                                    for (int fileNo = 0; fileNo < highestFileNo; fileNo++) {
                                        // Every year
                                        for (int year = earliestYear; year < currentYear; year++) {
                                            download(
                                                    DECISIONS_FILESYSTEM_LOCATION.getFilePath(),
                                                    courtCode,
                                                    courtName,
                                                    caseGroup,
                                                    caseNumber,
                                                    year,
                                                    fileNo);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    public static void download(String downloadFolderPath, String courtCode, String courtName, String caseGroup, long caseNumber, int year, int fileNo) {
        // EXAMPLE URL:  https://ukp.birosag.hu/portal-frontend/stream/birosagKod/0001/hatarozatAzonosito/Gfv.30155_2009_5//
        String downloadUrl = "https://ukp.birosag.hu/portal-frontend/stream/birosagKod/"
                + courtCode + "/hatarozatAzonosito/"
                + caseGroup + "."
                + caseNumber + "_"
                + year + "_"
                + fileNo + "//";
        String nameOfNewFile = caseGroup.toLowerCase() + "-" + caseNumber + "-" + year + "-" + fileNo;
        String pathOfNewFile;
        if (courtName.toLowerCase().split(" ").length >= 2) {
            pathOfNewFile = downloadFolderPath
                    + "/" + courtName.toLowerCase().split(" ")[0]
                    + "-"
                    + courtName.toLowerCase().split(" ")[1]
                    + "/" + nameOfNewFile;
        } else {
            pathOfNewFile = downloadFolderPath
                    + "/" + courtName.toLowerCase().split(" ")[0]
                    + "/" + nameOfNewFile;
        }

        System.out.println("DownloadURL is: " + downloadUrl);
        System.out.println("Path of new file is: " + pathOfNewFile);
        if ((previousSuccessfulDownloadsInfo != null && !containsDownloadResult(previousSuccessfulDownloadsInfo, downloadUrl))
                || (previousFailedDownloadsInfo != null && !containsDownloadResult(previousFailedDownloadsInfo, downloadUrl))) {
            return;
        }
        try {
            // Only download if it is not yet downloaded
            IOUtils.download(downloadUrl, pathOfNewFile);
            System.out.println("********************************Download successful!**********************************************");
            successfulDownloadsInfo.add(new DownloadResult(downloadUrl));
            IOUtils.writeString(successfulDownloadsInfo.toJSONString(),
                    DECISIONS_FILESYSTEM_LOCATION.getFilePath() + "/successful-downloads-info.json",
                    false);
        } catch (MuteUrlException mutUEx) {
            mutUEx.printStackTrace();
        } catch (IOException iOEx) {
            failedDownloadsInfo.add(new DownloadResult(downloadUrl));
            IOUtils.writeString(failedDownloadsInfo.toJSONString(),
                    DECISIONS_FILESYSTEM_LOCATION.getFilePath() + "/failed-downloads-info.json",
                    false);
            iOEx.printStackTrace();
        }
    }

    public static boolean containsDownloadResult(final List<DownloadResult> list, final String urlString) {
        return list.stream().anyMatch(dResult -> dResult.getUrlString().equals(urlString));
    }

}
