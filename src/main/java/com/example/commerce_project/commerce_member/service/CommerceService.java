package com.example.commerce_project.commerce_member.service;

import com.example.commerce_project.commerce_member.entity.Commerce;
import com.example.commerce_project.commerce_member.model.CommerceInput;
import com.example.commerce_project.commerce_member.model.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface CommerceService extends UserDetailsService {

    boolean register(CommerceInput parameter);

    /**
     * uuid에 해당하는 계정을 활성화 함.
     * */
    boolean emailAuth(String uuid);

    /**
     * 입력한 이메일로 비밀번호 초기화 정보를 전송
     */
    boolean sendResetPassword(ResetPasswordInput parameter);

    /**
     * 입력받은 uuid 에 대해서 password로 초기화 함
     */
    boolean resetPassword(String uuid, String password);

    /**
     * 입력 받은 uuid 값이 유효한지 확인
     */
    boolean checkResetPassword(String uuid);


}
