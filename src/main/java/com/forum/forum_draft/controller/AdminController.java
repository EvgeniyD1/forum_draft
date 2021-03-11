package com.forum.forum_draft.controller;

import com.forum.forum_draft.dao.UserRepository;
import com.forum.forum_draft.domain.Role;
import com.forum.forum_draft.domain.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String userList(@RequestParam(required = false) String search, Model model) {
        List<User> users;
        if (search != null && !search.isEmpty()) {
            users = userRepository.findUsersByUsername(search);
        } else {
            users = userRepository.findAllUsersOrderByUsernameAsc();
        }
        model.addAttribute("users", users);
        model.addAttribute("search", search);
        return "userList";
    }

    @GetMapping("{user}")
    public String userEdit(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    public String userSave(
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user) {
        user.getRoles().clear();
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
        return "redirect:/admin";
    }
}
