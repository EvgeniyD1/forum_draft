package com.forum.forum_draft.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "m_messages")
public class Message {

    public Message(String topicName, String text, String tag, User user) {
        this.topicName = topicName;
        this.text = text;
        this.tag = tag;
        this.author = user;
    }

    public Message() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String text;

    @Column
    private String tag;

    @Column(name = "topic_name")
    private String topicName;

    @Column
    private Timestamp time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @Column
    private String filename;

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }
}
