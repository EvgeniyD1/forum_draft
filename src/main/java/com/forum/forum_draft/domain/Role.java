package com.forum.forum_draft.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}

/*простой мапинг
@Data
@Entity
@Table(name = "m_roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

*//*    Супер ошибка))
    @Column(name = "user_id")
    private Long userId;*//*

    @Column(name = "role_name")
    private String roleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}*/
