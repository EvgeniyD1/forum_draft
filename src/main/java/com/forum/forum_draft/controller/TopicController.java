package com.forum.forum_draft.controller;

import com.forum.forum_draft.domain.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/topic")
public class TopicController {

    @GetMapping("/{message}")
    public String getTopic(@PathVariable Message message, Model model){
        model.addAttribute("message", message);
        return "topic";
    }
}
