package com.forum.forum_draft.service;

import com.forum.forum_draft.dao.CommentRepository;
import com.forum.forum_draft.domain.Comment;
import com.forum.forum_draft.domain.Message;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"comment"})
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    @Cacheable(value = "comment")
    public List<Comment> findByMessageOrderByIdDesc(Message message) {
        return commentRepository.findByMessageOrderByIdDesc(message);
    }

    @Override
    @CacheEvict(value = "comment", allEntries = true)
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    @Cacheable(value = "comment")
    public Optional<Comment> findById(Long commentId) {
        return commentRepository.findById(commentId);
    }


}
