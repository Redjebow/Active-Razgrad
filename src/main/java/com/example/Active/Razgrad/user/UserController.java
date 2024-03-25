package com.example.Active.Razgrad.user;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {
    public UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/add")
    public String addMealForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/submit")
    public ModelAndView submitUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model ){
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return new ModelAndView("register");
        }
        userService.saveUser(userService.makeCryotedPassword(user));

        return new ModelAndView("result");
    }
}
