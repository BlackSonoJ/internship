package org.example.apigateway.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/user")
    public String userFallback() {
        return "User Service is currently unavailable. Please try again later.";
    }

    @GetMapping("/notification")
    public String notificationFallback() {
        return "Notification Service is currently unavailable. Please try again later.";
    }
}
