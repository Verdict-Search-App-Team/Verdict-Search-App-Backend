package com.szalay.opencourtwebapp;

import com.szalay.opencourtwebapp.db.DecisionDto;
import com.szalay.opencourtwebapp.db.DecisionRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@RestController
class HomeController {

    //private static DecisionDatabase decisionDatabase = new DecisionDatabase();
    private final DecisionRepository decisionRepository;
    private List<Decision> resultsList;
//    private String mainSearchedTerm = null;

    public HomeController(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
        this.resultsList = new ArrayList();
    }


    @RequestMapping("/home")
    @ResponseBody
    public ModelAndView home() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("home", model);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView results(@RequestParam String searchedTerm, @RequestParam String hatarozatreszChoice) throws ClassNotFoundException {
        Map<String, Object> model = new HashMap<>();
        System.out.println(hatarozatreszChoice);
        //TODO decisionDatabase.fullSearch(searchedTerm);
        if (hatarozatreszChoice != null) {
            switch (hatarozatreszChoice) {
                case "teljeshatarozatban":
                    fillResultsList(decisionRepository.findByHatarozatStringCleanContaining(searchedTerm), searchedTerm);
                    break;
                case "bevezetoben":
                    fillResultsList(decisionRepository.findByBevezetoContaining(searchedTerm), searchedTerm);
                    break;
                case "rendelkezoben":
                    fillResultsList(decisionRepository.findByRendelkezoContaining(searchedTerm), searchedTerm);
                    break;
                case "tenyallasban":
                    fillResultsList(decisionRepository.findByTenyallasContaining(searchedTerm), searchedTerm);
                    break;
                case "jogiindokolasban":
                    fillResultsList(decisionRepository.findByJogiindokolasContaining(searchedTerm), searchedTerm);
                    break;
                case "zaroreszben":
                    fillResultsList(decisionRepository.findByZaroContaining(searchedTerm), searchedTerm);
                    break;
            }
            model.put("results", resultsList);
        }

        return new ModelAndView("results", model);

    }


    @RequestMapping("/{ugyszam}")
    public ModelAndView decision(@PathVariable("ugyszam") String ugyszam) {
        Map<String, Object> model = new HashMap<>();
        model.put("decisions", decisionRepository.findByUgyszam(ugyszam));
        return new ModelAndView("decisions", model);
    }

    public void fillResultsList(List<DecisionDto> decisionDtoList, String searchedTerm) {
        String context = "getContext method while ciklus nem futott";
        String[] myMondatArray = null;
        ListIterator<DecisionDto> decisionDtoListIterator = decisionDtoList.listIterator();
        try {
            while (decisionDtoListIterator.hasNext()) {

//            if(
//            decisionDtoListIterator.next().ugyszam == null ||
//                    decisionDtoListIterator.next().hatarozatStringClean == null ||
//                    decisionDtoListIterator.next().bevezeto == null ||
//                    decisionDtoListIterator.next().rendelkezo == null ||
//                    decisionDtoListIterator.next().tenyallas == null ||
//                    decisionDtoListIterator.next().jogiindokolas == null ||
//                    decisionDtoListIterator.next().zaro == null ||
//                    decisionDtoListIterator.next().birosagneve == null ||
//                    decisionDtoListIterator.next().birosagSzekhelye == null ||
//                    decisionDtoListIterator.next().ugyTipus == null ||
//                    decisionDtoListIterator.next().eljarasTipus,
//                    decisionDtoListIterator.next().eljarasSzakasz,
//                    decisionDtoListIterator.next().targy,
//                    decisionDtoListIterator.next().dontes,
//                    decisionDtoListIterator.next().dontesmasodfok,
//                    decisionDtoListIterator.next().donteselsofok,
//
//
//            decisionDtoListIterator.next() == null) {
//                break;
//            }

                myMondatArray = decisionDtoListIterator.next().hatarozatStringClean.split("<br>");

                for (String mondat : myMondatArray) {
                    if (mondat.contains(searchedTerm)) {
                        context = "[...] " + " " + mondat + " [...]";
                        context = context.replace(searchedTerm, "<mark>" + searchedTerm + "</mark>");
                        System.out.println(context);

                    }

                }

                resultsList.add(new Decision(
                        decisionDtoListIterator.next().ugyszam,
                        decisionDtoListIterator.next().hatarozatStringClean,
                        decisionDtoListIterator.next().bevezeto,
                        decisionDtoListIterator.next().rendelkezo,
                        decisionDtoListIterator.next().tenyallas,
                        decisionDtoListIterator.next().jogiindokolas,
                        decisionDtoListIterator.next().zaro,
                        decisionDtoListIterator.next().birosagneve,
                        decisionDtoListIterator.next().birosagSzekhelye,
                        decisionDtoListIterator.next().ugyTipus,
                        decisionDtoListIterator.next().eljarasTipus,
                        decisionDtoListIterator.next().eljarasSzakasz,
                        decisionDtoListIterator.next().targy,
                        decisionDtoListIterator.next().dontes,
                        decisionDtoListIterator.next().dontesmasodfok,
                        decisionDtoListIterator.next().donteselsofok,
                        context
                ));
            }
        } catch (NoSuchElementException exception) {
            System.out.println("NoSuchElementException");
        }


    }

}
