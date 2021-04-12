package com.forum.forum_draft.service;

import com.forum.forum_draft.domain.Message;
import com.forum.forum_draft.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MessageService {

    Page<Message> findByTag(String tag, Pageable pageable);

    Page<Message> findAllMessages(Pageable pageable);

    Optional<Message> findById(Long messageId);

    void save(Message message);

    Page<Message> messageList(String search, Pageable pageable);

    Page<Message> findAllBySubscribe(User user, Pageable pageable);
}
