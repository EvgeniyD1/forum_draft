package com.forum.forum_draft.controller;

import com.forum.forum_draft.domain.Message;
import com.forum.forum_draft.domain.User;
import com.forum.forum_draft.service.DownloadService;
import com.forum.forum_draft.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
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
import java.util.Map;

@Controller
public class MainController {

    private final MessageService messageService;
    private final DownloadService downloadService;

    public MainController(MessageService messageService, DownloadService downloadService) {
        this.messageService = messageService;
        this.downloadService = downloadService;
    }

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("/main")
    public String mainPage(
            @RequestParam(required = false) String search,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 9) Pageable pageable,
            Model model) {
        Page<Message> messages = messageService.messageList(search, pageable);
        model.addAttribute("messages", messages);
        model.addAttribute("url", "/main");
        model.addAttribute("search", search);
        return "main";
    }

    @PostMapping("/main")
    public String addNewMessage(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 9) Pageable pageable,
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult, Model model,
            @RequestParam("file") MultipartFile file) throws IOException {
        model.addAttribute("url", "/main");
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
            Page<Message> messages = messageService.findAllMessages(pageable);
            model.addAttribute("messages", messages);
            return "main";
        } else {
            message.setAuthor(user);
            message.setTime(new Timestamp(new Date().getTime()));
            if (file != null && !ObjectUtils.isEmpty(file.getOriginalFilename())) {
                message.setFilename(downloadService.getNewFileName(file));
            }
            messageService.save(message);
            model.addAttribute("message", null);
        }
        return "redirect:" + "/main";
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
            Message mFDb = messageService.findById(messageId).orElseThrow();
            message.setId(mFDb.getId());
            message.setAuthor(mFDb.getAuthor());
            message.setTime(new Timestamp(new Date().getTime()));
            if (file != null && !ObjectUtils.isEmpty(file.getOriginalFilename())) {
                message.setFilename(downloadService.getNewFileName(file));
            } else {
                message.setFilename(mFDb.getFilename());
            }
            messageService.save(message);
            model.addAttribute("message", null);
        }
        return "redirect:" + "/main";
    }

}
