package com.forum.forum_draft.dao;

import com.forum.forum_draft.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>,
        CrudRepository<Message, Long>,
        PagingAndSortingRepository<Message, Long> {

    @Query("select message from Message message where message.tag = :tag order by message.id desc ")
    List<Message> findByTagDesc(@Param("tag") String tag);

    @Query("select message from Message message left join fetch message.author order by message.id desc")
//    @Query("select message from Message message order by message.id desc")
    List<Message> fndAllMessagesDesc();

}
