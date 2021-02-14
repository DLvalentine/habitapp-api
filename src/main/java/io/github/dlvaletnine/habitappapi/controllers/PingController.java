package io.github.dlvaletnine.habitappapi.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

// Simple controller for health check(s)
@RestController
public class PingController {

    @GetMapping("/ping")
    public String Ping() {
        return "Ping!";
    }

}
