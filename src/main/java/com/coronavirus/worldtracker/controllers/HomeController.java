package com.coronavirus.worldtracker.controllers;

import com.coronavirus.worldtracker.services.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class HomeController {

    @Autowired
    CovidDataService service;

    @GetMapping(path = "/")
    public String home(Model model) throws IOException, InterruptedException {
        model.addAttribute("locationStats", service.getVirusData());
        model.addAttribute("totalReportedCases", service.getTotalCases());
        model.addAttribute("totalNewCases", service.getTotalNewCases());
        return "home";
    }

}
