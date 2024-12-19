package com.dcess.authentication.service;

import com.dcess.authentication.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HomeService{

    // Retrieves the authenticated user's details from the security context and generates a greeting message.
    public Map<String, String> loadUser() throws UsernameNotFoundException {
        // Retrieves the current authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        Map<String, String> response = new HashMap<>();
        // Checks if the principal is an instance of User and generates a personalized message
        if (principal instanceof User) {
            User userDetails = (User) principal;
            response.put("message", "Hello " + userDetails.getFullName());
        } else {
            // If the principal is not a User instance, uses the principal's string representation
            response.put("message", "Hello " + principal.toString());
        }
        return response;
    }
}
