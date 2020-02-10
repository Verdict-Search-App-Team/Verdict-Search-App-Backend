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
    public ModelAndView results(@RequestParam String searchedTerm) throws ClassNotFoundException {
        Map<String, Object> model = new HashMap<>();
        //TODO decisionDatabase.fullSearch(searchedTerm);
        model.put("results", decisionRepository.findByBirosagneveContaining(searchedTerm));
        return new ModelAndView("results", model);
    }

//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String home() {
//        return "home";
//    }

    @RequestMapping(value = "/decisions", method = RequestMethod.POST)
    public ModelAndView decision() {
        Map<String, Object> model = new HashMap<>();
        model.put("decisions", decisionRepository.findAll());
        return new ModelAndView("decisions", model);
    }

//    @RequestMapping(value = "/carsdeleted", method = RequestMethod.POST)
//    public String search(@RequestParam String licence) {
//        decisionDatabase.removeCar(licence);
//        return "redirect:/cars";
//    }

}
