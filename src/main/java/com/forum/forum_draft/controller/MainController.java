package com.forum.forum_draft.controller;

import com.forum.forum_draft.dao.MessageRepository;
import com.forum.forum_draft.domain.Message;
import com.forum.forum_draft.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;

    private final MessageRepository messageRepository;

    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("/main")
    public String init(@RequestParam(required = false) String search, Model model) {
        List<Message> messages;
        if (search != null && !search.isEmpty()) {
            messages = messageRepository.findByTag(search);
        } else {
            messages = messageRepository.fndAllMessagesDesc();
        }
        model.addAttribute("messages", messages);
        model.addAttribute("search", search);
        return "main";
    }


    @PostMapping("/main")
    public String addNewMessage(
            @AuthenticationPrincipal User user,
            @RequestParam String topicName,
            @RequestParam String text,
            @RequestParam String tag, Model model,
            @RequestParam("file") MultipartFile file) throws IOException {
        Message message = new Message(topicName, text, tag, user);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuid = UUID.randomUUID().toString();
            String newFileName = uuid + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath.concat("/") + newFileName));
            message.setFilename(newFileName);
        }
        message.setTime(new Timestamp(new Date().getTime()));
        messageRepository.save(message);
        List<Message> messages = messageRepository.fndAllMessagesDesc();
        model.addAttribute("messages", messages);
        return "main";
    }

    /*@GetMapping("/topic/{message}")
    public String getTopic(@PathVariable Message message, Model model){
        model.addAttribute("message", message);
        return "topic";
    }*/


}
