package com.szalay.opencourtwebapp;

import com.szalay.opencourtwebapp.db.DecisionDto;
import com.szalay.opencourtwebapp.db.DecisionRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.szalay.opencourtwebapp.InitialDataFilePaths.DECISIONS_FILESYSTEM_LOCATION;

public class ImportUtils {

//    @Autowired
//    static DecisionRepository decisionRepository;

    private static final List<File> decisionFiles = new ArrayList<>();
    private static final List<File> directoriesToBeChecked = new ArrayList<>();


    public static List<File> getListOfFilesContainingDecisions(String mainFolderPath) {
        try {
            File[] files = new File(mainFolderPath).listFiles();
            System.out.println(new File(mainFolderPath).listFiles().toString());
            for (File file :
                    files) {
                    directoriesToBeChecked.add(file);
            }
            while (!directoriesToBeChecked.isEmpty()) {
                recursiveSearch();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decisionFiles;
    }

    public static void recursiveSearch() {
        for (int i = 0; i < directoriesToBeChecked.size(); i++) {
            File file = directoriesToBeChecked.get(i);
            // Checking that it really is a directory
            if (file.isDirectory()) {
                for (File innerFile : Objects.requireNonNull(file.listFiles())) {
                    if (innerFile.isDirectory()) {
                        directoriesToBeChecked.add(innerFile);
                    } else if (innerFile.getName().contains(".rtf") && !decisionFiles.contains(innerFile)) {
                        decisionFiles.add(innerFile);
                        System.out.println("Added " + innerFile.getName() + "to list");
                    }
                }
            }
            directoriesToBeChecked.remove(file);
        }
    }

    public static void saveAllFromFilesystemToDB (DecisionRepository decisionRepository) {
        List<File> fileList = ImportUtils.getListOfFilesContainingDecisions(DECISIONS_FILESYSTEM_LOCATION.getFilePath());
        for (File file : fileList) {
            // Only construct new DecisionDto object if the record doesn't already exist in the database
            if (decisionRepository.findByCaseNumber(TextProcessor.extractCaseNumber(file.getName())).size() == 0){
                decisionRepository.save(new DecisionDto(file));
                System.out.println("Saved " + file.getName() + " to DB");
            }
        }
    }

}



