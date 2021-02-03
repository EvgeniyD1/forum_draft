package com.forum.forum_draft.controller;

import com.forum.forum_draft.dao.UserRepository;
import com.forum.forum_draft.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("{user}")
    public String userList(@PathVariable User user, Model model) {
        model.addAttribute("username", user.getUsername());
        return "userPagee";
    }

    @GetMapping("/userPageEdit/{user}")
    public String userEdit(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        return "userPageEdit";
    }

    @PostMapping
    public String userSave(@RequestParam String username,
                           @RequestParam("userId") User user){
        user.setUsername(username);
        userRepository.save(user);
        return "redirect:/user/" + user.getId().toString();
    }
}
