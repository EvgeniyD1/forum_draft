package com.forum.forum_draft.controller;

import com.forum.forum_draft.dao.MessageRepository;
import com.forum.forum_draft.domain.Message;
import com.forum.forum_draft.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
            /*@RequestParam String topicName,
            @RequestParam String text,
            @RequestParam String tag*/
            @Valid Message message,
            BindingResult bindingResult, Model model,
            @RequestParam("file") MultipartFile file) throws IOException {

        message.setAuthor(user);

        if (bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
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
            model.addAttribute("message", null);
        }
        List<Message> messages = messageRepository.fndAllMessagesDesc();
        model.addAttribute("messages", messages);
        return "main";
    }

    @GetMapping("/main/update/{message}")
    public String getUpdateMessage(
            @PathVariable Message message,
            Model model){
        model.addAttribute("message", message);
        return "updateMessage";
    }

    @PostMapping("/main/update/{message}")
    public String updateMessage(
            @PathVariable Message message,
            @RequestParam String topicName,
            @RequestParam String text,
            @RequestParam String tag, Model model,
            @RequestParam("file") MultipartFile file) throws IOException{
        message.setTopicName(topicName);
        message.setText(text);
        message.setTag(tag);
        message.setTime(new Timestamp(new Date().getTime()));
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
        messageRepository.save(message);
        return "redirect:/main";
    }


}
