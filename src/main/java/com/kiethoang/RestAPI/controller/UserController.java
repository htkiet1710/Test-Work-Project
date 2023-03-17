package com.kiethoang.RestAPI.controller;

import com.kiethoang.RestAPI.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private List<User> users = new ArrayList<User>();

    @PostMapping("/user")
    public User create(@RequestBody User user) {
        users.add(user);

        return user;
    }
    @GetMapping("/users")
    public List<User> getAll() {
        return users;
    }

}
