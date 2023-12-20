package com.finalproject.bttd.Utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.finalproject.bttd.entity.User;
import com.finalproject.bttd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
@Slf4j
public class FileUpload {
    private final AmazonS3Client amazonS3Client;

    @Autowired
    private UserRepository userRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("user_id") String user_id) {
        try {
            log.info("uploadFile user_id : " + user_id);

            String fileName=file.getOriginalFilename();
            String uniqueName = makeUniqueFilename(fileName);

            String fileUrl= "http://" + bucket + "/test" +uniqueName;
            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket,uniqueName,file.getInputStream(),metadata);

           Optional<User> user = userRepository.findByuser_id(user_id);
            if (user.isPresent()) {
                User userEntity = user.get();
                userEntity.setPhoto(uniqueName);
                userRepository.save(userEntity);
            } else {
                log.error("User not found with id: " + user_id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String makeUniqueFilename(String fileName) {
        // 파일 확장자 추출
        String extension = "";
        int extensionIndex = fileName.lastIndexOf('.');
        if (extensionIndex >= 0) {
            extension = fileName.substring(extensionIndex);
        }

        // UUID를 사용하여 파일 이름 생성
        String uuid = UUID.randomUUID().toString();

        return uuid + extension; // 예: "123e4567-e89b-12d3-a456-426614174000.jpg"
    }



}
