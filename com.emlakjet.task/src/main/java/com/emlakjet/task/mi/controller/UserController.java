package com.emlakjet.task.mi.controller;

import com.emlakjet.task.mi.dao.impl.UserDao;
import com.emlakjet.task.mi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
class UserController {

    @Autowired
    UserDao userDao;

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/users")
    List<User> all() {
        return userDao.getAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return userDao.save(newUser);
    }

    // Single item

    @GetMapping("/users/{id}")
    User one(@PathVariable int id) {
        return userDao.get(id);

    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable int id) {

        return userDao.save(
                new User(newUser.getName(),newUser.getSurname(),newUser.getEmail()) );

    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable int id) {
        userDao.delete(id);
    }
}