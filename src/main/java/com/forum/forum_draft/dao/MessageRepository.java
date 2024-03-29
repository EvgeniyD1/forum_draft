package com.forum.forum_draft.dao;

import com.forum.forum_draft.domain.Message;
import com.forum.forum_draft.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>,
        CrudRepository<Message, Long>,
        PagingAndSortingRepository<Message, Long> {

    Page<Message> findByTag(@Param("tag") String tag, Pageable pageable);

    @Query(value = "select message from Message message left join fetch message.author",
    countQuery = "select count (message) from Message message")
//    countQuery нужен для работы Page с одним параметром pageable
    Page<Message> findAllMessages(Pageable pageable);

    @Query(value = "select message from Message message left join fetch message.author author " +
            "left join author.subscribers sub where sub = :user",
            countQuery = "select count (message) from Message message")
    Page<Message> findAllBySubscribe(User user, Pageable pageable);

}
