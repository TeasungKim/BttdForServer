package com.finalproject.bttd.apicontroller.admin;

import com.finalproject.bttd.entity.User;
import com.finalproject.bttd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/add")
        public void add(@RequestBody User user){
        String newPassword = passwordEncoder.encode(user.getUser_password());
        user.setUser_password(newPassword);
            User created = userRepository.save(user);
        }

}
