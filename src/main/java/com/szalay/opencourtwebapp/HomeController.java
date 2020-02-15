package com.szalay.opencourtwebapp;

import com.szalay.opencourtwebapp.db.DecisionRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
class HomeController {

    //private static DecisionDatabase decisionDatabase = new DecisionDatabase();
    private final DecisionRepository decisionRepository;

    public HomeController(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
    }

//    @RequestMapping("/results")
//    public @ResponseBody ModelAndView results() {
//        model.put("results", decisionDatabase.getDecisions());
//        return  new ModelAndView("results", model);
//    }

    @RequestMapping("/home")
    @ResponseBody
    public ModelAndView home() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("home", model);
    }

//    @RequestMapping(value="/reset", method = RequestMethod.POST)
//    public @ResponseBody String reset() {
//        decisionDatabase.reset();
//        return "redirect: home";
//    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView results(@RequestParam String searchedTerm, @RequestParam String hatarozatreszChoice) throws ClassNotFoundException {
        Map<String, Object> model = new HashMap<>();
        System.out.println(hatarozatreszChoice);
        //TODO decisionDatabase.fullSearch(searchedTerm);
        if (hatarozatreszChoice != null) {
            switch (hatarozatreszChoice) {
                case "teljeshatarozatban":
                    model.put("results", decisionRepository.findByHatarozatStringCleanContaining(searchedTerm));
                    break;
                case "bevezetoben":
                    model.put("results", decisionRepository.findByBevezetoContaining(searchedTerm));
                    break;
                case "rendelkezoben":
                    model.put("results", decisionRepository.findByRendelkezoContaining(searchedTerm));
                    break;
                case "tenyallasban":
                    model.put("results", decisionRepository.findByTenyallasContaining(searchedTerm));
                    break;
                case "jogiindokolasban":
                    model.put("results", decisionRepository.findByJogiindokolasContaining(searchedTerm));
                    break;
                case "zaroreszben":
                    model.put("results", decisionRepository.findByZaroContaining(searchedTerm));
                    break;
            }
        }
        return new ModelAndView("results", model);
    }

//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String home() {
//        return "home";
//    }

    String URLem = "/valami";

//    @RequestMapping(value = "/decisions", method = RequestMethod.POST)
//    public ModelAndView decision(@RequestParam String ugyszam) {
//        Map<String, Object> model = new HashMap<>();
//        model.put("decisions", decisionRepository.findByUgyszam(ugyszam));
//        return new ModelAndView("decisions", model);
//    }

    @RequestMapping(value = "/url/{id}", method=RequestMethod.POST)
    public ModelAndView decision(@PathVariable("id") String birosagneve, @RequestParam String ugyszam) {
        Map<String, Object> model = new HashMap<>();
        model.put("decisions", decisionRepository.findByUgyszam(ugyszam));
        return new ModelAndView("decisions", model);
    }


//    @RequestMapping(value="/url/{id}", method=RequestMethod.GET)
//    public String method(@PathVariable("id") String id) {
//        System.out.println("the url value : "+id );
//        return "Whatever";
//    }


//    @RequestMapping(value = "/carsdeleted", method = RequestMethod.POST)
//    public String search(@RequestParam String licence) {
//        decisionDatabase.removeCar(licence);
//        return "redirect:/cars";
//    }

}
