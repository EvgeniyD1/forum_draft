package com.forum.forum_draft.controller;

import com.forum.forum_draft.dao.UserRepository;
import com.forum.forum_draft.domain.User;
import com.forum.forum_draft.service.DownloadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final DownloadService downloadService;

    public UserController(UserRepository userRepository, DownloadService downloadService) {
        this.userRepository = userRepository;
        this.downloadService = downloadService;
    }

    @GetMapping("{user}")
    public String userList(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("messages", user.getMessages());
        return "userProfile";
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
            user.setPictureName(downloadService.getNewFileName(file));
        }
        userRepository.save(user);
        return "redirect:/user/" + user.getId().toString();
    }
}
