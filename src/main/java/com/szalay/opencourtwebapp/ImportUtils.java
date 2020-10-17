package com.szalay.opencourtwebapp;

import com.szalay.opencourtwebapp.db.DecisionDto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.szalay.opencourtwebapp.InitialDataFilePaths.DECISIONS_FILESYSTEM_LOCATION;

public class ImportUtils {

//    @Autowired
//    static DecisionRepository decisionRepository;



    public static List<File> getListOfFilesContainingDecisions(String mainFolderPath) {
        List<File> initialFileList = new ArrayList<>();
        File[] fileNames = new File(mainFolderPath).listFiles();
        if (fileNames != null) {
            for (File file : fileNames) {
                if (file.isDirectory()) {
                    File[] fileNames2 = new File(file.getAbsolutePath()).listFiles();
                    assert fileNames2 != null;
                    for (File file2 : fileNames2) {
                        if (!file2.getName().contains(".txt")) {
                            initialFileList.add(file2);
                        }
                    }
                }
            }
        }
        return initialFileList;

    }


}



