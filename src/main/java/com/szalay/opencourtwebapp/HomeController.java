package com.szalay.opencourtwebapp;

import com.szalay.opencourtwebapp.db.DecisionDto;
import com.szalay.opencourtwebapp.db.DecisionRepository;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.szalay.opencourtwebapp.InitialDataFilePaths.DECISIONS_FILESYSTEM_LOCATION;

@RestController
@RequestMapping("/")
class HomeController {

//    HttpHeaders headers = new HttpHeaders();

    public final DecisionRepository decisionRepository;

    public HomeController(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
        saveAllFromFilesystemToDB();
    }

    @CrossOrigin
    @GetMapping("/home")
    public Object home() {
        return decisionRepository.count();
    }

    @CrossOrigin
    @GetMapping("/results")
    public List<DecisionSearchResult> search(@RequestParam String searchedTerm) {
        List<DecisionDto> decisionDtoList = decisionRepository.findByDecisionTextContaining(searchedTerm);
        List<DecisionSearchResult> resultsList = fillResultsList(decisionDtoList, searchedTerm);
        return resultsList;
    }

    @CrossOrigin
    @GetMapping("/{ugyszam}")
    public DecisionDto decision(@PathVariable("ugyszam") String ugyszam) {
        return decisionRepository.findByCaseNumber(ugyszam).get(0);
    }

    public List<DecisionSearchResult> fillResultsList(List<DecisionDto> decisionDtoList, String searchedTerm) {
        List<DecisionSearchResult> resultsList = new ArrayList();
        String contextString = "ContextString variable is empty";
        for (DecisionDto decisionDto : decisionDtoList) {
            String[] tempParagraphArray;
            try {
                tempParagraphArray = decisionDto.decisionText.split("<br>");
                for (String paragraph : tempParagraphArray) {
                    if (paragraph.contains(searchedTerm)) {
                        contextString = "[...] " + " " + paragraph + " [...]";
                        contextString = contextString.replace(searchedTerm, "<mark>" + searchedTerm + "</mark>");
                    }
                }
                resultsList.add(new DecisionSearchResult(decisionDto, contextString, searchedTerm));
            } catch (NoSuchElementException exception) {
                System.out.println("NoSuchElementException");
            }
        }
        return resultsList;
    }

    public void saveAllFromFilesystemToDB () {
        List<File> fileList = ImportUtils.getListOfFilesContainingDecisions(DECISIONS_FILESYSTEM_LOCATION.getFilePath());
        for (File file : fileList) {
            // Only construct new DecisionDto object if the record doesn't already exist in the database
            if (decisionRepository.findByCaseNumber(TextProcessor.extractCaseNumber(file.getName())).size() == 0){
                decisionRepository.save(new DecisionDto(file));
            }
        }
    }

}
