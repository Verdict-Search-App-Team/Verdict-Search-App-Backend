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

    private final DecisionRepository decisionRepository;

    public HomeController(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
//        headers.setAccessControlAllowOrigin("*");
    }

    @CrossOrigin
    @GetMapping("/home")
    public List<Object> home() {
        List<Object> data = new ArrayList<>();
        data.add(decisionRepository.count());
        return data;
    }

    @CrossOrigin
    @GetMapping("/results")
    public List<DecisionSearchResult> search(@RequestParam String searchedTerm) {
        List<DecisionDto> decisionDtoList = decisionRepository.findByHatarozatszovegContaining(searchedTerm);
        List<DecisionSearchResult> resultsList = fillResultsList(decisionDtoList, searchedTerm);
        return resultsList;
    }

    @CrossOrigin
    @GetMapping("/{ugyszam}")
    public List<Decision> decision(@PathVariable("ugyszam") String ugyszam) {
        List<Decision> decisionList = new ArrayList();
        decisionList.add(new Decision(decisionRepository.findByUgyszam(ugyszam).get(0).hatarozatszoveg));
        return decisionList;
    }

    public List<DecisionSearchResult> fillResultsList(List<DecisionDto> decisionDtoList, String searchedTerm) {
        List<DecisionSearchResult> resultsList = new ArrayList();
        String contextString = "ContextString variable is empty";
        for (DecisionDto decisionDto : decisionDtoList) {
            String[] tempParagraphArray;
            try {
                tempParagraphArray = decisionDto.hatarozatszoveg.split("<br>");
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
