package com.finalproject.bttd.service;

import com.finalproject.bttd.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(User user, String siteURL) {
        String subject = "Please verify your registration";
        String senderName = "Your Company";
        String mailContent = "<p>Dear " + user.getUser_id() + ",</p>";
        mailContent += "<p>Please click the link below to verify your registration:</p>";

        String verifyURL = siteURL + "/verify?code=" + user.getVerificationToken();

        mailContent += "<h3><a href=\"" + verifyURL + "\">VERIFY</a></h3>";
        mailContent += "<p>Thank you<br>The Company Team</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("kingkim59@naver.com", senderName);
            helper.setTo(user.getUser_id());
            helper.setSubject(subject);
            helper.setText(mailContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new MailSendException("Failed to send email", e);
        }  catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }





}
