package com.ipi.jva320.controller;

import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;


    @GetMapping("/")
        public String home(final ModelMap model) {
        Long countSalaries = salarieAideADomicileService.countSalaries();
        model.put("countSalaries", countSalaries);
            return "home";
        }



    }


