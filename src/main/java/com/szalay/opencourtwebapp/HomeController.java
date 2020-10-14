package com.szalay.opencourtwebapp;

import com.szalay.opencourtwebapp.db.DecisionDto;
import com.szalay.opencourtwebapp.db.DecisionRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/")
class HomeController {

    HttpHeaders headers = new HttpHeaders();

    private final DecisionRepository decisionRepository;

    public HomeController(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
        headers.setAccessControlAllowOrigin("http://localhost:4200");
    }

    @GetMapping("/home")
    public ResponseEntity<Object> home() {
        List<Object> data = new ArrayList<>();
        data.add(decisionRepository.count());
        return ResponseEntity.ok().headers(headers).body(data);
    }

    @GetMapping("/results")
    public ResponseEntity<Object> search(@RequestParam String searchedTerm) {
        List<DecisionDto> decisionDtoList = decisionRepository.findByHatarozatszovegContaining(searchedTerm);
        List<DecisionSearchResult> resultsList = fillResultsList(decisionDtoList, searchedTerm);
        return ResponseEntity.ok().headers(headers).body(resultsList);
    }

    @GetMapping("/{ugyszam}")
    public ResponseEntity<Object> decision(@PathVariable("ugyszam") String ugyszam) {
        List<Decision> decisionList = new ArrayList();
        decisionList.add(new Decision(decisionRepository.findByUgyszam(ugyszam).get(0).hatarozatszoveg));
        return ResponseEntity.ok().headers(headers).body(decisionList);
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
