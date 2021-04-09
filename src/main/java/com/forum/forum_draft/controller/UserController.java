package com.forum.forum_draft.controller;

import com.forum.forum_draft.domain.User;
import com.forum.forum_draft.service.DownloadService;
import com.forum.forum_draft.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.IOException;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final DownloadService downloadService;

    public UserController(UserService userService, DownloadService downloadService) {
        this.userService = userService;
        this.downloadService = downloadService;
    }

    @GetMapping("{userId}")
    public String userList(@AuthenticationPrincipal User currentUser,
                           @PathVariable Long userId,
                           Model model) {
        User user = userService.findById(userId).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        boolean isSubscriber =
                user.getSubscribers().stream().anyMatch(user1 -> user1.getId().equals(currentUser.getId()));
        model.addAttribute("isSubscriber", isSubscriber);
//        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
//        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("messages", user.getMessages());
        return "userProfile";
    }

    @GetMapping("/subscribe/{user}")
    public String subscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ) {
        user.getSubscribers().add(currentUser);
        userService.save(user);
        return "redirect:/user/" + user.getId();
    }

    @GetMapping("/unsubscribe/{user}")
    public String unsubscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ) {
        user.getSubscribers().remove(currentUser);
        userService.save(user);
        return "redirect:/user/" + user.getId();
    }

    @GetMapping("/{type}/{userId}")
    public String subList(
            @PathVariable String type,
            @PathVariable Long userId,
            Model model
    ) {
        User user = userService.findById(userId).orElseThrow();
        model.addAttribute("userChannel", user);
        model.addAttribute("type", type);
        if ("subscriptions".equals(type)) {
            model.addAttribute("users", user.getSubscriptions());
        } else {
            model.addAttribute("users", user.getSubscribers());
        }
        return "subscriptions";
    }

    @GetMapping("/userPageEdit/{user}")
    public String userEdit(
            @PathVariable User user,
            Model model) {
        model.addAttribute("user", user);
        return "userPageEdit";
    }

    @PostMapping
    public String userUpdate(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String phoneNumber,
                             @RequestParam("userId") User user,
                             @RequestParam("file") MultipartFile file) throws IOException, ServletException {
        user.setUsername(username);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        if (file != null && !ObjectUtils.isEmpty(file.getOriginalFilename())) {
            user.setPictureName(downloadService.getNewFileName(file));
        }
        userService.save(user);
        /*пусть кикает, зато навбар обновится*/
        userService.logout();
        return "redirect:/login";
    }
}
