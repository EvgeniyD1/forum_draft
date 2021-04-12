package com.forum.forum_draft.controller;

import com.forum.forum_draft.domain.Message;
import com.forum.forum_draft.domain.User;
import com.forum.forum_draft.service.DownloadService;
import com.forum.forum_draft.service.MessageService;
import com.forum.forum_draft.service.UserService;
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
    private final UserService userService;

    public MainController(MessageService messageService,
                          DownloadService downloadService,
                          UserService userService) {
        this.messageService = messageService;
        this.downloadService = downloadService;
        this.userService = userService;
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

    @GetMapping("/main/sub/{userId}")
    public String subMessage(
            @PathVariable Long userId,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 9) Pageable pageable,
            Model model){
        User user = userService.findById(userId).orElseThrow();
        Page<Message> allBySubscribe = messageService.findAllBySubscribe(user, pageable);
        model.addAttribute("messages", allBySubscribe);
        model.addAttribute("url", "/main/sub/"+userId);
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
