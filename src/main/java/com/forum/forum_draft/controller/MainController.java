package com.forum.forum_draft.controller;

import com.forum.forum_draft.dao.MessageRepository;
import com.forum.forum_draft.domain.Message;
import com.forum.forum_draft.domain.User;
import com.forum.forum_draft.service.DownloadService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private final MessageRepository messageRepository;
    private final DownloadService downloadService;

    public MainController(MessageRepository messageRepository, DownloadService downloadService) {
        this.messageRepository = messageRepository;
        this.downloadService = downloadService;
    }

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("/main")
    public String mainPage(@RequestParam(required = false) String search, Model model) {
        List<Message> messages;
        if (search != null && !search.isEmpty()) {
            messages = messageRepository.findByTagDesc(search);
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
            @Valid Message message,
            BindingResult bindingResult, Model model,
            @RequestParam("file") MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            message.setAuthor(user);
            message.setTime(new Timestamp(new Date().getTime()));
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                message.setFilename(downloadService.getNewFileName(file));
            }
            messageRepository.save(message);
            model.addAttribute("message", null);
        }
        List<Message> messages = messageRepository.fndAllMessagesDesc();
        model.addAttribute("messages", messages);
        return "main";
    }

    @GetMapping("/main/update/{message}")
    public String getUpdateMessage(
            @PathVariable Message message,
            Model model) {
        model.addAttribute("message", message);
        return "updateMessage";
    }

    @PostMapping("/main/update/{id}")
    public String updateMessage(
            @PathVariable("id") Long messageId,
            @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
            return "updateMessage";
        } else {
            Message mFDb = messageRepository.findById(messageId).orElseThrow();
            message.setId(mFDb.getId());
            message.setAuthor(mFDb.getAuthor());
            message.setTime(new Timestamp(new Date().getTime()));
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                message.setFilename(downloadService.getNewFileName(file));
            } else {
                message.setFilename(mFDb.getFilename());
            }
            messageRepository.save(message);
            model.addAttribute("message", null);
        }
        return "redirect:" + "/main";
    }

}
