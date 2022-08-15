package com.contactmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping("/home")
    public String home(Model model){

        model.addAttribute("title", "Home Page");

        return "index";
    }

    @GetMapping("/signup")
    public String signUp(Model model){

        model.addAttribute("title", "Sign Up Page");

        return "signup";
    }

}
