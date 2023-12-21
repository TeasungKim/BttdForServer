package com.finalproject.bttd.service;

import com.finalproject.bttd.Utils.Utility;
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

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private EmailService emailService;


    @Transactional
    public User create(UserDto userDto, HttpServletRequest request) {
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
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        userRepository.save(user);
        
        
        String siteURL = Utility.getSiteURL(request);
      
        emailService.sendVerificationEmail(user, siteURL);

        return null;
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
