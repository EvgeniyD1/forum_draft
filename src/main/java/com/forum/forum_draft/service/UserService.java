package com.forum.forum_draft.service;

import com.forum.forum_draft.dao.UserRepository;
import com.forum.forum_draft.domain.Role;
import com.forum.forum_draft.domain.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@CacheConfig(cacheNames = {"user"})
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest httpServletRequest;

    public UserService(UserRepository userRepository,
                       MailSender mailSender,
                       PasswordEncoder passwordEncoder,
                       HttpServletRequest httpServletRequest) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
        this.httpServletRequest = httpServletRequest;
    }

    public void logout() throws ServletException {
        httpServletRequest.logout();
    }

    @Cacheable(value = "user")
    public List<User> adminList(String search){
        if (search != null && !search.isEmpty()) {
            return findUsersByUsername(search);
        } else {
            return findAllUsersOrderByUsernameAsc();
        }
    }

    @Override
    @Cacheable(value = "user")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @CacheEvict(value = "user", allEntries = true)
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public boolean addUser(User user){
        User byUsername = findByUsername(user.getUsername());
        if (byUsername!=null){
            return false;
        }
        user.setRoles(Collections.singleton(Role.USER));
        user.setPhoneNumber("empty");
        user.setPictureName("logo-2.png");
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        if (!ObjectUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Hello, %s!\n" +
                            "Welcome to Forum_Draft. Please follow the link to activate your account " +
                            "http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation code", message);
        }
        return true;
    }

    @CacheEvict(value = "user", allEntries = true)
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public boolean activateUser(String code) {
        User user = findByActivationCode(code);
        if (user==null){
            return false;
        }
        user.setActivationCode(null);
        user.setActive(true);
        save(user);
        return true;
    }

    @Cacheable(value = "user")
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Cacheable(value = "user")
    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    @Cacheable(value = "user")
    public User findByActivationCode(String code){
        return userRepository.findByActivationCode(code);
    }

    @Cacheable(value = "user")
    public List<User> findAllUsersOrderByUsernameAsc() {
        return userRepository.findAllUsersOrderByUsernameAsc();
    }

    @Cacheable(value = "user")
    public List<User> findUsersByUsername(String username){
        return userRepository.findUsersByUsername(username);
    }

    @CacheEvict(value = "user", allEntries = true)
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void save(User user) {
        userRepository.save(user);
    }

}
