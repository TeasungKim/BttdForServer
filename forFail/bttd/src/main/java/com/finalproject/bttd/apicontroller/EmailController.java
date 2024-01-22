package com.finalproject.bttd.apicontroller;

import com.finalproject.bttd.Utils.Utility;
import com.finalproject.bttd.apiresponse.ApiResponse;
import com.finalproject.bttd.dto.EmailDto;
import com.finalproject.bttd.dto.UserDto;
import com.finalproject.bttd.entity.User;
import com.finalproject.bttd.repository.UserRepository;
import com.finalproject.bttd.service.EmailService;
import com.finalproject.bttd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

import static com.finalproject.bttd.apiresponse.ApiResponse.FAIL_STATUS;
import static com.finalproject.bttd.apiresponse.ApiResponse.SUCCESS_STATUS;

@RestController
public class EmailController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;



   // @PostMapping("/api/authenticateEmail")
  //  public ResponseEntity<ApiResponse<String>> authenticateEmail(@RequestBody EmailDto emailDto, HttpServletRequest request){
   //     String userId = emailDto.getUser_id();
   //     Optional<User> newUserOpt = userRepository.findByuser_id(userId);

   //     if (newUserOpt.isPresent()) {
   //         User newUser = newUserOpt.get();

   //         String token = UUID.randomUUID().toString();
     //       newUser.setVerificationToken(token);
       //     userRepository.save(newUser);

         //   String siteURL = Utility.getSiteURL(request);
           // emailService.sendVerificationEmail(newUser, siteURL);

  //          ApiResponse<String> response = new ApiResponse<>();
    //        response.setStatus(SUCCESS_STATUS);  // SUCCESS_STATUS는 성공 상태를 나타내는 상수
      //      response.setMessage("Verification email sent.");
        //    response.setData(null);

          //  return ResponseEntity.ok(response);
  //      } else {
    //        ApiResponse<String> response = new ApiResponse<>();
      //      response.setStatus(FAIL_STATUS);  // FAIL_STATUS는 실패 상태를 나타내는 상수
        //    response.setMessage("User not found.");
          //  response.setData(null);

//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  //      }
 //   }





}
