package com.finalproject.bttd.apicontroller;

import com.finalproject.bttd.entity.User;
import com.finalproject.bttd.service.EmailService;
import com.finalproject.bttd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private UserService userService;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam("code") String verificationCode) {
        boolean isVerified = userService.verifyUser(verificationCode);
        if (isVerified) {
            return ResponseEntity.ok("Account verified successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired verification token.");
        }
    }
}
