package com.szalay.OpencourtWebApp;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    private static DecisionDatabase decisionDatabase = new DecisionDatabase();
    private Map<String, Object> model = new HashMap<>();

//    @RequestMapping("/results")
//    public @ResponseBody ModelAndView results() {
//        model.put("results", decisionDatabase.getDecisions());
//        return  new ModelAndView("results", model);
//    }

    @RequestMapping("/home")
    public @ResponseBody ModelAndView home() {
        model.put("home", decisionDatabase.getDecisions());
        return new ModelAndView("home", model);
    }

    @RequestMapping(value="/reset", method = RequestMethod.POST)
    public @ResponseBody String reset() {
        decisionDatabase.reset();
        return "redirect: home";
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
//    public String search(@RequestParam String searchedTerm) {
//        decisionDatabase.fullSearch(searchedTerm);
//        return "redirect:results";
    public ModelAndView results(@RequestParam String searchedTerm) throws ClassNotFoundException {
        decisionDatabase.fullSearch(searchedTerm);
        model.put("results", decisionDatabase.getDecisions());
       return new ModelAndView("results", model);
    }

//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String home() {
//        return "home";
//    }

    @RequestMapping(value = "/decision", method = RequestMethod.POST)
    public ModelAndView decision() {
        model.put("decisions", decisionDatabase.getDecisions());
        return new ModelAndView("decisions", model);
    }

//    @RequestMapping(value = "/carsdeleted", method = RequestMethod.POST)
//    public String search(@RequestParam String licence) {
//        decisionDatabase.removeCar(licence);
//        return "redirect:/cars";
//    }

}
