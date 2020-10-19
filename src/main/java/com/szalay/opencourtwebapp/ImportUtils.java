package com.szalay.opencourtwebapp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImportUtils {

//    @Autowired
//    static DecisionRepository decisionRepository;

    private static List<File> decisionFiles = new ArrayList<>();
    private static List<File> directoriesToBeChecked = new ArrayList<>();


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
        } catch (Exception e){
            e.printStackTrace();
        }

        return decisionFiles;
    }

    public static void recursiveSearch() {
        for (int i = 0; i < directoriesToBeChecked.size(); i++) {
            File file = directoriesToBeChecked.get(i);
            if (file.isDirectory() && !directoriesToBeChecked.contains(file)) {
                directoriesToBeChecked.add(file);
            } else {
                directoriesToBeChecked.remove(file);
                if (file.getName().contains(".rtf")) {
                    decisionFiles.add(file);
                }
            }
        }
    }


}



