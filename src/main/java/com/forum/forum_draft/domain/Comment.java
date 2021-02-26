package com.forum.forum_draft.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "m_comments")
public class Comment {

    public Comment(Message message, User author, String text) {
        this.message = message;
        this.author = author;
        this.text = text;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "message_id")
    private Message message;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @Column
    private String text;

    @Column
    private Timestamp time;

    @Column(name = "parent_id")
    private Long parentId;

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }
}
