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
import java.util.Optional;

@Controller
@RequestMapping("/topic")
public class TopicController {

    private final CommentRepository commentRepository;

    public TopicController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping("/{message}")
    public String getTopic(@PathVariable Message message, Model model){
        List<Comment> comments = commentRepository.findByMessageOrderByIdDesc(message);
        model.addAttribute("message", message);
        model.addAttribute("comments", comments);
        return "topic";
    }

    @PostMapping("/{message}")
    public String addNewComment(
            @PathVariable Message message,
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam Long parentId,
            @RequestParam String test,
            Model model){
        Optional<Comment> commentOptional = commentRepository.findById(parentId);
        Comment comment;
        if (commentOptional.isPresent()){
            if (test.equals("update")){
                comment = commentOptional.orElseThrow();
                comment.setText(text);
                comment.setTime(new Timestamp(new Date().getTime()));
                commentRepository.save(comment);
            } else if (test.equals("save")){
                comment = new Comment(message, user, text);
                comment.setTime(new Timestamp(new Date().getTime()));
                comment.setParentId(parentId);
                commentRepository.save(comment);
            }
        } else {
            comment = new Comment(message, user, text);
            comment.setTime(new Timestamp(new Date().getTime()));
            commentRepository.save(comment);
        }
        /*List<Comment> comments = commentRepository.findByMessageOrderByIdDesc(message);*/
        /*model.addAttribute("comments", comments);*/
        model.addAttribute("message", message);
        return "redirect:/topic/{message}";
    }

/*    @PutMapping("/{message}")
    public String updateComment(
            @PathVariable Message message,
            @AuthenticationPrincipal User user,
            @RequestParam String textUpdate,
            @RequestParam Long commentId,
            Model model){
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.setText(textUpdate);
        comment.setTime(new Timestamp(new Date().getTime()));
        commentRepository.save(comment);
        List<Comment> comments = commentRepository.findByMessageOrderByIdDesc(message);
        model.addAttribute("comments", comments);
        model.addAttribute("message", message);
        return "redirect:/topic/{message}";
    }*/
}
