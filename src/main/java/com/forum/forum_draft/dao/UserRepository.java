package com.forum.forum_draft.dao;

import com.forum.forum_draft.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends JpaRepository<User, Long>,
        CrudRepository<User, Long>,
        PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);

    User findByActivationCode(String code);
}
