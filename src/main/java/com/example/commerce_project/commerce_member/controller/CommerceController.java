package com.example.commerce_project.commerce_member.controller;

import com.example.commerce_project.commerce_member.model.CommerceInput;
import com.example.commerce_project.commerce_member.model.ResetPasswordInput;
import com.example.commerce_project.commerce_member.service.CommerceService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.PostRemove;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class CommerceController {
    private final CommerceService commerceService;

    @RequestMapping("/commerce/login")
    public String login() {

        return "commerce/login";
    }

    @GetMapping("/commerce/find/password")
    public String findPassword() {

        return "commerce/find_password";
    }

    @PostMapping("/commerce/find/password")
    public String findPasswordSubmit(Model model, ResetPasswordInput parameter) {

        boolean result = false;
        try {
            result = commerceService.sendResetPassword(parameter);
        } catch (Exception e) {

        }
        model.addAttribute("result", result);

        return "commerce/find_password_result";
    }

    @GetMapping("/commerce/register")
    public String register() {

        return "commerce/register";
    }

    @PostMapping("/commerce/register")
    public String registerSubmit(Model model, HttpServletRequest request, CommerceInput parameter) {

        boolean result = commerceService.register(parameter);
        model.addAttribute("result", result);

        return "commerce/register_complete";
    }

    //http://www.naver.com/news/list.do?id=123
    @GetMapping("/commerce/email-auth")
    public String emailAuth(Model model, HttpServletRequest request) {

        String uuid = request.getParameter("id");
        System.out.println(uuid);

        boolean result = commerceService.emailAuth(uuid);
        model.addAttribute("result", result);

        return "commerce/email_auth";

    }
    @GetMapping("/commerce/info")
    public String commerceInfo() {

        return "commerce/info";
    }

    @GetMapping("/commerce/reset/password")
    public String resetPassword(Model model, HttpServletRequest request) {

        String uuid = request.getParameter("id");

        boolean result = commerceService.checkResetPassword(uuid);

        model.addAttribute("result", result);

        return "commerce/reset_password";
    }

    @PostMapping("/commerce/reset/password")
    public String resetPasswordSubmit(Model model, ResetPasswordInput parameter) {

        boolean result = false;
        try {
            result = commerceService.resetPassword(parameter.getId(), parameter.getPassword());
        } catch (Exception e) {

        }

        model.addAttribute("result", result);

        return "commerce/reset_password_result";
    }
}
