package io.github.dlvalentine.habitappapi.models;

// Simple class to demonstrate/test JSON serialization on health check path
public class Ping {
    private String message = "Ping!";

    public Ping() {}

    public String getMessage() {
        return message;
    }

}