package com.example.commerce_project.components;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class MailComponents {

    private final JavaMailSender javaMailSender;
//    public void sendMail() {
//
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo("tjddbs1412@naver.com");
//        msg.setSubject("안녕하세요. 회원가입을 축하드립니다.");
//        msg.setText("안녕하세요 회원가입 인증이 완료되셨습니다.");
//
//        javaMailSender.send(msg);
//
//    }

    public boolean sendMail(String mail, String subject, String text) {

        boolean result = false;
        MimeMessagePreparator msg = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setTo(mail);
                mimeMessageHelper.setSubject(subject);
                mimeMessageHelper.setText(text, true);
            }
        };

        try{
            javaMailSender.send(msg);
            result = true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
