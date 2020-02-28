package com.szalay.opencourtwebapp;

import com.szalay.opencourtwebapp.db.DecisionDto;
import com.szalay.opencourtwebapp.db.DecisionRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@RestController
class HomeController {

    private final DecisionRepository decisionRepository;

    public HomeController(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
    }

    @RequestMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("home");
    }


    @RequestMapping("/results")
    public ModelAndView search(@RequestParam String searchedTerm, @RequestParam String hatarozatreszChoice) {
        List<DecisionDto> decisionDtoList = new ArrayList<>();
        if (hatarozatreszChoice != null) {
            switch (hatarozatreszChoice) {
                case "teljeshatarozatban":
                    decisionDtoList = decisionRepository.findByHatarozatStringCleanContaining(searchedTerm);
                    break;
                case "bevezetoben":
                    decisionDtoList = decisionRepository.findByBevezetoContaining(searchedTerm);
                    break;
                case "rendelkezoben":
                    decisionDtoList = decisionRepository.findByRendelkezoContaining(searchedTerm);
                    break;
                case "tenyallasban":
                    decisionDtoList = decisionRepository.findByTenyallasContaining(searchedTerm);
                    break;
                case "jogiindokolasban":
                    decisionDtoList = decisionRepository.findByJogiindokolasContaining(searchedTerm);
                    break;
                case "zaroreszben":
                    decisionDtoList = decisionRepository.findByZaroContaining(searchedTerm);
                    break;
            }

        }
        List<DecisionSearchResult> resultsList = fillResultsList(decisionDtoList, searchedTerm);
        Map<String, Object> model = new HashMap<>();
        model.put("results", resultsList);
        return new ModelAndView("results", model);

    }

    @RequestMapping("/{ugyszam}")
    public ModelAndView decision(@PathVariable("ugyszam") String ugyszam) {
        Map<String, Object> model = new HashMap<>();
        model.put("decisions", decisionRepository.findByUgyszam(ugyszam));
        return new ModelAndView("decisions", model);
    }

    public List<DecisionSearchResult> fillResultsList(List<DecisionDto> decisionDtoList, String searchedTerm) {
        List<DecisionSearchResult> resultsList = new ArrayList();
        String contextString = "ContextString variable is empty";
        for (DecisionDto decisionDto : decisionDtoList) {
            String[] tempParagraphArray;
            try {
                tempParagraphArray = decisionDto.hatarozatStringClean.split("<br>");
                for (String paragraph : tempParagraphArray) {
                    if (paragraph.contains(searchedTerm)) {
                        contextString = "[...] " + " " + paragraph + " [...]";
                        contextString = contextString.replace(searchedTerm, "<mark>" + searchedTerm + "</mark>");

                    }

                }
                resultsList.add(new DecisionSearchResult(decisionDto, contextString));

            } catch (NoSuchElementException exception) {
                System.out.println("NoSuchElementException");

            }

        }
        return resultsList;

    }


}
