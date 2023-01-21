package com.m9devsolutions.ichurchlicense.controller;

import com.m9devsolutions.ichurchlicense.service.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final LicenseService service;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("licenses", service.getAllLicences());
        return "home";
    }
}
