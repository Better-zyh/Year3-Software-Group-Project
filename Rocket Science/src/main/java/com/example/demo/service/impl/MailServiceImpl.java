package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.example.demo.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

@Service
@Slf4j
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private VerificationServiceImpl verificationService;

    @Override
    public void sendSimpleMail(String mailTo, String user_id){
        SimpleMailMessage message=new SimpleMailMessage();
        Random random = new Random();
        String code= String.valueOf(random.nextInt(9000)+1000);
        verificationService.generateVerificationCode(code,user_id);
        message.setFrom("308684051@qq.com");
        message.setTo(mailTo);
        message.setSubject("Your verification");
        message.setText("Your verification code is: "+code+", Please activate your account");
        mailSender.send(message);
        log.info("Email has been sent successfully");
    }
//    @Override
//    public void sendSimpleMail(String mailTo){
//        Properties props = new Properties();
//            props.setProperty("mail.smtp.ssl.enable","true");
//            props.setProperty("mail.smtp.auth","true");
//            props.setProperty("mail.host","smtp.qq.com");
//            props.setProperty("mail.smtp.port","465");
//            props.setProperty("mail.transport.protocol","smtp");
//        Session session= Session.getDefaultInstance(props, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication(){
//                return new PasswordAuthentication("308684051@qq.com","qiwufwkcahdabgjg");
//
//            }
//        });
//        try{
//            Message message= new MimeMessage(session);
//            message.setFrom(new InternetAddress("308684051@qq.com"));
//            message.addRecipient(Message.RecipientType.TO,new InternetAddress(mailTo));
//            message.setSubject("verfication code");
//            message.setText("TEXT");
//            Transport.send(message);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }

}
