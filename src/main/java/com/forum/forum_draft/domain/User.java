package com.forum.forum_draft.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {
        "messages", "subscribers", "subscriptions"
})
@ToString(exclude = {
        "messages", "subscribers", "subscriptions"
})
@Entity
@Table(name = "m_users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "username cannot be empty")
    @Length(max = 50, message = "username is too long")
    private String username;

    @Column
    @NotBlank(message = "password cannot be empty")
    private String password;

    @Column
    private boolean active;

    @Column
    @Email(message = "email is not correct")
    @NotBlank(message = "email cannot be empty")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "picture_name")
    private String pictureName;

    @Column(name = "activation_code")
    private String activationCode;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "m_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size=10)
    @Column(name = "role_name")
    private Set<Role> roles;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> messages;

    @ManyToMany
    @JoinTable(
            name = "m_user_subscriptions",
            joinColumns = {@JoinColumn(name = "subscriber_id")},
            inverseJoinColumns = {@JoinColumn(name = "subscription_id")}
    )
//    @Fetch(FetchMode.SUBSELECT)
    private Set<User> subscribers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "m_user_subscriptions",
            joinColumns = {@JoinColumn(name = "subscription_id")},
            inverseJoinColumns = {@JoinColumn(name = "subscriber_id")}
    )
//    @Fetch(FetchMode.SUBSELECT)
    private Set<User> subscriptions = new HashSet<>();

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
}
