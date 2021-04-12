package com.forum.forum_draft.service;

import com.forum.forum_draft.dao.MessageRepository;
import com.forum.forum_draft.domain.Message;
import com.forum.forum_draft.domain.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"message"})
public class MessageServiceImpl  implements MessageService{

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    @Cacheable(value = "message")
    public Page<Message> findByTag(String tag, Pageable pageable) {
        return messageRepository.findByTag(tag, pageable);
    }

    @Override
    @Cacheable(value = "message")
    public Page<Message> findAllMessages(Pageable pageable) {
        return messageRepository.findAllMessages(pageable);
    }

    @Override
    @Cacheable(value = "message")
    public Optional<Message> findById(Long messageId) {
        return messageRepository.findById(messageId);
    }

    @Override
    @CacheEvict(value = "message", allEntries = true)
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void save(Message message) {
        messageRepository.save(message);
    }

    @Override
    @Cacheable(value = "message")
    public Page<Message> messageList(String search, Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return findByTag(search, pageable);
        } else {
            return findAllMessages(pageable);
        }
    }

    @Override
    @Cacheable(value = "message")
    public Page<Message> findAllBySubscribe(User user, Pageable pageable) {
        return messageRepository.findAllBySubscribe(user, pageable);
    }


}
