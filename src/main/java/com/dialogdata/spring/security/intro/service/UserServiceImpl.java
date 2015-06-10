package com.dialogdata.spring.security.intro.service;

import com.dialogdata.spring.security.intro.data.dao.UserRepository;
import com.dialogdata.spring.security.intro.data.entities.User;
import com.dialogdata.spring.security.intro.transport.UserTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserTO> getAll() {
        final List<UserTO> userTOs = new LinkedList<>();

        final List<User> allUsers = userRepository.findAll();
        userTOs.addAll(allUsers.stream().map(this::buildUserTO).collect(Collectors.toList()));

        return userTOs;
    }

    @Override
    public UserTO get(final Integer id) {
        LOGGER.info("Retrieving the user with the ID '{}'...", id);

        User user = userRepository.findOne(id);

        return buildUserTO(user);
    }

    private UserTO buildUserTO(final User user) {
        final UserTO userTO = new UserTO();

        userTO.setId(user.getId());
        userTO.setUserName(user.getUserName());
        userTO.setFirstName(user.getFirstName());
        userTO.setLastName(user.getLastName());

        return userTO;
    }
}
