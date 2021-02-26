package com.forum.forum_draft.dao;

import com.forum.forum_draft.domain.Comment;
import com.forum.forum_draft.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>,
        CrudRepository<Comment, Long>, PagingAndSortingRepository<Comment, Long> {

    List<Comment> findByMessageOrderByIdDesc(Message message);
}
