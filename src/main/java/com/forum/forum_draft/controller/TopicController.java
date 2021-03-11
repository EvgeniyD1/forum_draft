package com.forum.forum_draft.controller;

import com.forum.forum_draft.dao.CommentRepository;
import com.forum.forum_draft.domain.Comment;
import com.forum.forum_draft.domain.Message;
import com.forum.forum_draft.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/topic")
public class TopicController {

    private final CommentRepository commentRepository;

    public TopicController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping("/{message}")
    public String getTopic(@PathVariable Message message, Model model) {
        List<Comment> comments = commentRepository.findByMessageOrderByIdDesc(message);
        model.addAttribute("message", message);
        model.addAttribute("comments", comments);
        return "topic";
    }

    @PostMapping("/addComment")
    public String addNewComment(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam("messageId") Message message) {
        Comment comment = new Comment(message, user, text);
        comment.setTime(new Timestamp(new Date().getTime()));
        commentRepository.save(comment);
        return "redirect:/topic/" + message.getId().toString();
    }

    @PostMapping("/updateComment")
    public String updateComment(
            @RequestParam String text,
            @RequestParam("messageId") Message message,
            @RequestParam Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.setText(text);
        comment.setTime(new Timestamp(new Date().getTime()));
        commentRepository.save(comment);
        return "redirect:/topic/" + message.getId().toString();
    }

    @PostMapping("/commentingComment")
    public String commentingComment(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam("messageId") Message message,
            @RequestParam Long parentId) {
        Comment comment = new Comment(message, user, text);
        comment.setTime(new Timestamp(new Date().getTime()));
        comment.setParentId(parentId);
        commentRepository.save(comment);
        return "redirect:/topic/" + message.getId().toString();
    }

}
