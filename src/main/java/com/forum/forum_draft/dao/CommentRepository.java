package com.forum.forum_draft.dao;

import com.forum.forum_draft.domain.Comment;
import com.forum.forum_draft.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>,
        CrudRepository<Comment, Long>, PagingAndSortingRepository<Comment, Long> {

    @Query("select comment from Comment comment left join fetch comment.author where comment.message =:message " +
            "order by comment.id desc ")
    List<Comment> findByMessageOrderByIdDesc(@Param("message") Message message);
}
