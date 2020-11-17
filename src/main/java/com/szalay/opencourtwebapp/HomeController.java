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

    public final DecisionRepository decisionRepository;
    private String searchedTermGlobal;

    public HomeController(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
        if (OpencourtwebappApplication.isRead()){
            ImportUtils.saveAllFromFilesystemToDB(decisionRepository);
        }
    }

    @CrossOrigin
    @GetMapping("/")
    public Object home() {
        List<Object> homePageContent = new ArrayList<>();
        List<DecisionDto> popularDecisions = new ArrayList<>();
        homePageContent.add(decisionRepository.count());
        for (int i = 0; i < decisionRepository.findByViewCountGreaterThanEqualOrderByViewCountDesc(1).size(); i++) {
            popularDecisions.add(decisionRepository.findByViewCountGreaterThanEqualOrderByViewCountDesc(1).get(i));
            if (i >= 10) {
                break;
            }
        }
        homePageContent.add(popularDecisions);
        return homePageContent;
    }

    @CrossOrigin
    @GetMapping("/results")
    public List<DecisionSearchResult> search(@RequestParam String searchedTerm) {
        this.searchedTermGlobal = searchedTerm;
        List<DecisionSearchResult> resultsList = new ArrayList<>();
        List<DecisionDto> resultDecisions = new ArrayList<>();
        for (DecisionDto decisionDto : decisionRepository.findByFrequentSearchKeywordsContainingOrderByViewCountDesc(searchedTerm)) {
            // Find the context of the searched term
            String contextString = getSearchContext(decisionDto, searchedTerm);
            // Add result to resultlist
            resultsList.add(new DecisionSearchResult(decisionDto, contextString, searchedTerm));
            resultDecisions.add(decisionDto);
        }
        for (DecisionDto decisionDto : decisionRepository.findByDecisionTextContainingOrderByViewCountDesc(searchedTerm)) {
            if (!resultDecisions.contains(decisionDto)) {
                // Find the context of the searched term
                String contextString = getSearchContext(decisionDto, searchedTerm);
                // Add result to resultlist
                resultsList.add(new DecisionSearchResult(decisionDto, contextString, searchedTerm));
                resultDecisions.add(decisionDto);
            }
        }
        return resultsList;
    }

    @CrossOrigin
    @GetMapping("/{ugyszam}")
    public DecisionDto decision(@PathVariable("ugyszam") String ugyszam) {
        // Find decision
        DecisionDto myDecision = null;
        if (decisionRepository.findByCaseNumber(ugyszam).get(0) != null) {
            myDecision = decisionRepository.findByCaseNumber(ugyszam).get(0);
        }
        // Add current searched term to frequently searched terms
        assert myDecision != null;
        decisionRepository.setFrequentSearchKeywordsFor(this.searchedTermGlobal
                + myDecision.frequentSearchKeywords + "_", myDecision.caseNumber);
        // Add +1 to decision view count
        decisionRepository.setViewCountsFor(myDecision.viewCount + 1, myDecision.caseNumber);
        return myDecision;
    }

    private String getSearchContext(DecisionDto decisionDto, String searchedTerm) {
        String contextString = "ContextString variable is empty";
        String[] tempParagraphArray;
        try {
            tempParagraphArray = decisionDto.decisionText.split("\n");
            int lastFoundIndex = 0;
            int smallestDiff = Integer.MAX_VALUE;
            for (int i = 0; i < tempParagraphArray.length; i++) {
                if (tempParagraphArray[i].contains(searchedTerm) && (i - lastFoundIndex) < smallestDiff) {
                    contextString = tempParagraphArray[lastFoundIndex] + "\n" + tempParagraphArray[i];
                    smallestDiff = i - lastFoundIndex;
                    lastFoundIndex = i;
                }
            }
        } catch (NoSuchElementException exception) {
            System.out.println("NoSuchElementException");
        }
        return contextString;
    }


}
