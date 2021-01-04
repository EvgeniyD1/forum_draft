package com.forum.forum_draft.controller;

import com.forum.forum_draft.dao.MessageRepository;
import com.forum.forum_draft.domain.Message;
import com.forum.forum_draft.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class InitController {

    private final MessageRepository messageRepository;

    public InitController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public String home(){
        return "home";
    }

    @GetMapping("/main")
    public String init(@RequestParam(required = false) String search, Model model) {
        List<Message> messages;
        if (search != null && !search.isEmpty()) {
            messages = messageRepository.findByTag(search);
        } else {
            messages = messageRepository.findAll();
        }
        model.addAttribute("messages", messages);
        model.addAttribute("search", search);
        return "main";
    }


    @PostMapping("/main")
    public String addNewMessage(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Model model) {
        Message message = new Message(text, tag, user);
        messageRepository.save(message);
        List<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "main";
    }


}
