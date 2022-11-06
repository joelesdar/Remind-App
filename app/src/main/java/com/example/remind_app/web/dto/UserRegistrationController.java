package com.example.remind_app.web.dto;

import com.example.remind_app.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
    private UserService userService;
    @GetMapping
    public String showRegistrationForm(){
        return "registration";
    }

    public UserRegistrationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @PostMapping
    public String verifyUserAccount(@ModelAttribute("usuario")UserRegistrationDto userRegistrationDto){
        userService.userSave(userRegistrationDto);
        return "redirect:/registration?success";
    }

    @ModelAttribute("usuario")
    public UserRegistrationDto userRegistrationDto(){
        return new UserRegistrationDto();
    }
}