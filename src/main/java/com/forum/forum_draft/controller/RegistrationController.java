package com.forum.forum_draft.controller;

import com.forum.forum_draft.domain.User;
import com.forum.forum_draft.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam String password2,
            @Valid User user,
            BindingResult bindingResult,
            Model model){
        boolean isConfirmEmpty = password2.isEmpty();
        if (isConfirmEmpty){
            model.addAttribute("password2Error", "password confirmation cannot be empty");
        }
        if (user.getPassword()!=null && !user.getPassword().equals(password2)){
            model.addAttribute("passwordError","password are different");
            return "registration";
        }
        if (isConfirmEmpty || bindingResult.hasErrors()){
            Map<String, String> registrationErrors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(registrationErrors);
            return "registration";
        }
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exist");
            return "registration";
        }
        return "verify";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model){
        boolean isActivated = userService.activateUser(code);
        if (isActivated){
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation failed");
        }
        return "login";
    }

}
