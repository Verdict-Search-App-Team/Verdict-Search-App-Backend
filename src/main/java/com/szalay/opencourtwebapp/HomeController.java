package com.szalay.opencourtwebapp;

import com.szalay.opencourtwebapp.db.DecisionDto;
import com.szalay.opencourtwebapp.db.DecisionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/")
class HomeController {

//    HttpHeaders headers = new HttpHeaders();

    public final DecisionRepository decisionRepository;

    public HomeController(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
        ImportUtils.saveAllFromFilesystemToDB(decisionRepository);
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
                tempParagraphArray = decisionDto.decisionText.split("\n");
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



}
