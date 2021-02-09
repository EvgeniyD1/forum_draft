package com.forum.forum_draft.controller;

import com.forum.forum_draft.dao.UserRepository;
import com.forum.forum_draft.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Controller
@RequestMapping("/user")
public class UserController {

    @Value("${upload.path}")
    private String uploadPath;

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("{user}")
    public String userList(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        return "userPagee";
    }

    @GetMapping("/userPageEdit/{user}")
    public String userEdit(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        return "userPageEdit";
    }

    @PostMapping
    public String userUpdate(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String phoneNumber,
                             @RequestParam("userId") User user,
                             @RequestParam("file") MultipartFile file) throws IOException {
        user.setUsername(username);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuid = UUID.randomUUID().toString();
            String newFileName = uuid + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath.concat("/") + newFileName));
            user.setPictureName(newFileName);
        }
        userRepository.save(user);
        return "redirect:/user/" + user.getId().toString();
    }
}
