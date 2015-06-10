package com.dialogdata.spring.security.intro.data.dao;

import com.dialogdata.spring.security.intro.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findOneByUserName(final String userName);
}