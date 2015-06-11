package com.dialogdata.spring.security.intro.presentation;

import com.dialogdata.spring.security.intro.service.UserService;
import com.dialogdata.spring.security.intro.transport.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping
    @PreAuthorize("authenticated AND hasRole('ROLE_MANAGER')")
    public Collection<UserTO> getAll() {
        return userService.getAll();
    }

    @RequestMapping(value = "/{id}")
    public @ResponseBody UserTO get(@PathVariable Integer id) {
        return userService.get(id);
    }
}
