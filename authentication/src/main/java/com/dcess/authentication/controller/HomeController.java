package com.dcess.authentication.controller;

import com.dcess.authentication.service.HomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    // return A ResponseEntity containing a map with a greeting message for the authenticated user.
    @GetMapping
    public ResponseEntity<Map<String, String>> home() {
        return ResponseEntity.ok(homeService.loadUser());
    }
}
