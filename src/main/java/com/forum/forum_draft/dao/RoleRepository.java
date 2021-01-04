package com.forum.forum_draft.dao;

import com.forum.forum_draft.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository /*extends JpaRepository<Role, Long>,
        CrudRepository<Role, Long>,
        PagingAndSortingRepository<Role, Long>*/ {
}
