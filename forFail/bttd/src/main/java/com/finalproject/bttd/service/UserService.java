package com.finalproject.bttd.service;

import com.finalproject.bttd.dto.UserDto;
import com.finalproject.bttd.entity.Role;
import com.finalproject.bttd.entity.User;
import com.finalproject.bttd.repository.RoleRepository;
import com.finalproject.bttd.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public User create(UserDto userDto) {
  //      passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    User user = userDto.toEntity();
   //     System.out.println(user.getUser_password());
   String newPassword = passwordEncoder.encode(user.getUser_password());
    user.setUser_password(newPassword);
    Role role = roleRepository.findByName("USER").get();
    user.setRoles(Collections.singletonList(role));
    if(user.getUser_id() == null){
        return null;
    }

    userRepository.save(user);




        return null;
    }



    //
}
