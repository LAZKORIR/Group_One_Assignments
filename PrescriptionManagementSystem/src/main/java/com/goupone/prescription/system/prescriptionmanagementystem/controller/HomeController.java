package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index";  // Renders index.html
    }
}

