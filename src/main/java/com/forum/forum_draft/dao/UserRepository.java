package com.forum.forum_draft.dao;

import com.forum.forum_draft.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,
        CrudRepository<User, Long>,
        PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);

    User findByActivationCode(String code);

    @Query("select user from User user order by user.username asc")
    List<User> findAllUsersOrderByUsernameAsc();

    List<User> findUsersByUsername(String username);
}
