package com.contactmanager.controller;

import com.contactmanager.helper.Message;
import com.contactmanager.model.User;
import com.contactmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    private UserRepository userRepository;

    @GetMapping("/home")
    public String home(Model model){

        model.addAttribute("title", "Home Page");
        return "index";
    }

    @GetMapping("/signup")
    public String signUp(Model model){

        model.addAttribute("title", "Sign Up Page");
        model.addAttribute("user", new User());

        return "signup";
    }

    @RequestMapping(value = "/doregister", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                               @RequestParam(value = "terms", defaultValue = "false") boolean terms,
                               Model model,
                               HttpSession session){
        try {
            if (!terms){
                System.out.println("You didn't select the checkbox" );
            }

            if (bindingResult.hasErrors()){
                model.addAttribute("user", user);
                return "signup";
            }

            user.setRole("ROLE_USER");
            user.setActive(true);
            user.setuPassword(passwordEncoder.encode(user.getuPassword()));

            User save = userRepository.save(user);


            model.addAttribute("user", new User());
            session.setAttribute("message", new Message("User Registered Successfully.", "alert-success"));

        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something Went Wrong", "alert-danger"));
            return "signup";
        }
        return "signup";
    }



    //custom login handler

    @GetMapping("/cuslogin")
    public String customLogin(Model model){

        model.addAttribute("title", "login Page");

        return "login";
    }

}
