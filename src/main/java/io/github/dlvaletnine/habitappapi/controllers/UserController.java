package io.github.dlvaletnine.habitappapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.dlvaletnine.habitappapi.models.HttpResponse;
import io.github.dlvaletnine.habitappapi.models.User;
import io.github.dlvaletnine.habitappapi.repo.UserRepository;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users/create")
    public HttpResponse createUser(@RequestParam(required = true) String name) {
        User u = new User();
        u.setName(name);
        try {
            userRepository.save(u);
            return new HttpResponse("Success", HttpStatus.OK.value());
        } catch (Exception e) {
            return new HttpResponse(
                String.format("Unable to create user: %1s", e.toString()),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }
}
