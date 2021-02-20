package io.github.dlvalentine.habitappapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.dlvalentine.habitappapi.models.User;
import io.github.dlvalentine.habitappapi.repos.UserRepository;
import io.github.dlvalentine.habitappapi.responses.HttpResponse;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping(path = "/user/create", consumes = "application/json")
    public HttpResponse createUser(@RequestBody(required = true) User user) {
        try {
            User u = new User();

            u.setName(user.getName());

            userRepository.save(u);

            return new HttpResponse("Success", HttpStatus.OK.value());
        } catch (Exception e) {
            return new HttpResponse(
                "Unable to create User, User may already exist.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString()
            );
        }
    }
}
