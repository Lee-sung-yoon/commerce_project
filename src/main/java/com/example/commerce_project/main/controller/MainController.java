package com.example.commerce_project.main.controller;

import com.example.commerce_project.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MailComponents mailComponents;
    @RequestMapping("/")
    public String index() {
//        String email = "tjddbs1412@naver.com";
//        String subject = "안녕하세요. 회원가입을 축하드립니다.";
//        String text = "<p>안녕하세요.</p><p>회원가입 인증이 완료되었습니다.</p>";
//
//        mailComponents.sendMail(email, subject, text);

        return "index";
    }
}
