package com.finalproject.bttd.cache.cacheController;

import com.finalproject.bttd.Utils.Utility;
import com.finalproject.bttd.apiresponse.ApiResponse;
import com.finalproject.bttd.cache.cacheDto.cacheDto;
import com.finalproject.bttd.cache.cacheDto.cacheDtos;
import com.finalproject.bttd.cache.repository.CacheRepository;
import com.finalproject.bttd.service.EmailService;
import com.finalproject.bttd.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.UUID;

import static com.finalproject.bttd.apiresponse.ApiResponse.SUCCESS_STATUS;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CacheController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;

    private final CacheRepository cacheRepository;

    @PostMapping("/cache/firstSave")
    public ResponseEntity<ApiResponse<String>> firstSave(@RequestBody cacheDto cachedto){
    cacheDto member = new cacheDto((cachedto.getEmail()));
    cacheDto savedMember = cacheRepository.save(member);

        ApiResponse<String> response = new ApiResponse<>();
        response.setStatus(SUCCESS_STATUS);  // SUCCESS_STATUS는 성공 상태를 나타내는 상수
        response.setMessage("가입 가능한 이메일 입니다");
        response.setData(null);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/cache/Save")
    public cacheDto save(@RequestBody cacheDto memberDto) {
        log.info("save controller : "+ memberDto);
        cacheDto member = new cacheDto(memberDto.getEmail(), memberDto.getPrivateToken(), memberDto.isEnable());
        cacheDto savedMember = cacheRepository.save(member);
        log.info("Controller save {}", savedMember);
        return savedMember;
    }

    @GetMapping("/cache/findAll")
    public cacheDtos findAll() {
        cacheDtos members = cacheRepository.findAll();
        log.info("Controller findAll {}", members);
        return members;
    }

    @GetMapping("/cache/sendEmail")
    public ResponseEntity<ApiResponse<String>> update(@RequestParam String email, HttpServletRequest request) {

        cacheDto member = cacheRepository.findById(email);
        if(member != null){
            String token = UUID.randomUUID().toString();
            member.setPrivateToken(token);
            cacheRepository.save(member);

            String siteURL = Utility.getSiteURL(request);
            emailService.sendVerificationEmail(member, siteURL);
        }

        ApiResponse<String> response = new ApiResponse<>();
        response.setStatus(SUCCESS_STATUS);  // SUCCESS_STATUS는 성공 상태를 나타내는 상수
        response.setMessage("인증 이메일을 전송하였습니다.");
        response.setData(null);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam("code") String verificationCode) {
        log.info("verify 1 :" + verificationCode);
        boolean isVerified = userService.verifyUser(verificationCode);
        if (isVerified) {
            return ResponseEntity.ok("Account verified successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired verification token.");
        }
    }


}
