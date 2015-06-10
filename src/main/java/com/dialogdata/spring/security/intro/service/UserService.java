package com.dialogdata.spring.security.intro.service;

import com.dialogdata.spring.security.intro.transport.UserTO;

import java.util.List;

public interface UserService {
    List<UserTO> getAll();

    UserTO get(Integer id);
}
