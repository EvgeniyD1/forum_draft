package com.forum.forum_draft.dao;

import com.forum.forum_draft.domain.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>,
        CrudRepository<User, Long>,
        PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);

//    @Override
//    @Query("select user from User user left join fetch user.roles where user.id = :id")
//    Optional<User> findById(@Param("id") Long id);

    User findByActivationCode(String code);

    @Query("select user from User user order by user.username asc")
    List<User> findAllUsersOrderByUsernameAsc();

    List<User> findUsersByUsername(String username);
}
