package com.forum.forum_draft.service;

import com.forum.forum_draft.domain.Comment;
import com.forum.forum_draft.domain.Message;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> findByMessageOrderByIdDesc(Message message);

    void save(Comment comment);

    Optional<Comment> findById(Long commentId);
}
