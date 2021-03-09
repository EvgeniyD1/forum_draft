package com.forum.forum_draft.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

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

    @Column(name = "topic_name")
    @Length(max = 255, message = "too long topic name")
    @NotBlank(message = "add a topic name")
    private String topicName;

    @Column
    @NotBlank(message = "add a message")
    private String text;

    @Column
    @Length(max = 255, message = "too long tag")
    @NotBlank(message = "add a tag")
    private String tag;

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
