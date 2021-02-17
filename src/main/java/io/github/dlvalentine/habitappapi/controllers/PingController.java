package io.github.dlvalentine.habitappapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.dlvalentine.habitappapi.models.Ping;

// Simple controller for health check(s)
@RestController
public class PingController {

    // Because "200" by itself is so boring, and Hello World is so cliche.
    @GetMapping("/ping")
    public String ping(@RequestParam(required = false, defaultValue = "") String text) {
        if (text.length() > 0) {
            return String.format("Ping: %1s!", text);
        }

        return "Ping!";
    }

    // Really only here because I'm learning Spring through this project - look, Spring
    // automatically serializes things~~~
    @GetMapping("/jsonping")
    public Ping jsonPing() {
        return new Ping();
    }

}
