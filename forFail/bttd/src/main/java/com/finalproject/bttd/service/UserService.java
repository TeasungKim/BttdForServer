package com.finalproject.bttd.service;

import com.finalproject.bttd.Utils.Utility;
import com.finalproject.bttd.apiresponse.ApiResponse;
import com.finalproject.bttd.dto.UserDto;
import com.finalproject.bttd.entity.Role;
import com.finalproject.bttd.entity.User;
import com.finalproject.bttd.password.PasswordValidator;
import com.finalproject.bttd.repository.RoleRepository;
import com.finalproject.bttd.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
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
    @Autowired
    private EmailService emailService;


    @Transactional
    public User create(UserDto userDto, HttpServletRequest request) {
        String userId = userDto.getUser_id();
        Optional<User> newUser = userRepository.findByuser_id(userId);
        ApiResponse<String> response = new ApiResponse<>();
        log.info("userID userservice: " + userId);
        log.info("newUser userService : " + newUser);

        if(newUser.get().isEnabled()) {
            String userName = userDto.getUser_name();
            String userAge = userDto.getUser_age();
            String userWeight = userDto.getUser_weight();
            String userPassword = userDto.getUser_password();

            User existUser = newUser.get();
            existUser.setUser_name(userName);
            existUser.setUser_age(userAge);
            existUser.setUser_weight(userWeight);

            if(PasswordValidator.isValid(userPassword)) {

                String newPassword = passwordEncoder.encode(userPassword);
                existUser.setUser_password(newPassword);
            } else {
                throw new IllegalArgumentException("Invalid password format");

            }
            //      passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            //     System.out.println(user.getUser_password());
            Role role = roleRepository.findByName("USER").get();
            existUser.setRoles(Collections.singletonList(role));

            return existUser;
        } else {
            throw new IllegalStateException("User not found or email not verified");

        }

    }


    public boolean verifyUser(String verificationCode) {
        // 데이터베이스에서 verificationCode와 일치하는 사용자 찾기
        User user = userRepository.findByVerificationToken(verificationCode);

        if (user != null && !user.isEnabled()) {
            // 사용자의 상태를 '활성화'로 업데이트
            user.setEnabled(true);
            user.setVerificationToken(null); // 인증 토큰을 null로 설정하여 인증 완료 표시
            userRepository.save(user);
            return true; // 인증 성공
        }

        return false; // 인증 실패
    }

    //
}
