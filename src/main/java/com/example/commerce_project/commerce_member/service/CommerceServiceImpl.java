package com.example.commerce_project.commerce_member.service;

import com.example.commerce_project.commerce_member.entity.Commerce;
import com.example.commerce_project.commerce_member.exception.CommerceNotEmailAuthException;
import com.example.commerce_project.commerce_member.model.CommerceInput;
import com.example.commerce_project.commerce_member.model.ResetPasswordInput;
import com.example.commerce_project.commerce_member.repository.CommerceRepository;
import com.example.commerce_project.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CommerceServiceImpl implements CommerceService{

    private final CommerceRepository commerceRepository;
    private final MailComponents mailComponents;

    /**
     * 회원 가입
     */
    @Override
    public boolean register(CommerceInput parameter) {

        Optional<Commerce> optionalCommerce = commerceRepository.findById(parameter.getUserId());
        if (optionalCommerce.isPresent()) {
            //현재 userId에 해당하는 데이터 존재
            return false;
        }

        String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());

        String uuid = UUID.randomUUID().toString();

        Commerce commerce = Commerce.builder()
                .userId(parameter.getUserId())
                .userEmail(parameter.getUserEmail())
                .userName(parameter.getUserName())
                .phone(parameter.getPhone())
                .password(encPassword)
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .build();
        commerceRepository.save(commerce);

        String email = parameter.getUserId();
        String subject = "회원 가입을 축하합니다.";
        String text = "<p>회원 가입을 축하합니다.</p><p>아래 링크를 클릭하셔서 가입을 완료 해주세요.</P>"
                + "<div><a target='_blank' href='http://localhost:8080/commerce/email-auth?id=" + uuid + "'>가입 완료</a></div>";
        mailComponents.sendMail(email, subject, text);


        return true;
    }
    @Override
    public boolean emailAuth(String uuid) {

        Optional<Commerce> optionalCommerce = commerceRepository.findByEmailAuthKey(uuid);
        if (!optionalCommerce.isPresent()) {
            return false;
        }

        Commerce commerce = optionalCommerce.get();

        if (commerce.isEmailAuthYn()) {
            return false;
        }

        commerce.setEmailAuthYn(true);
        commerce.setEmailAuthDt(LocalDateTime.now());
        commerceRepository.save(commerce);

        return true;
    }

    @Override
    public boolean sendResetPassword(ResetPasswordInput parameter) {

        Optional<Commerce> optionalCommerce = commerceRepository.findByUserIdAndUserName(parameter.getUserId(), parameter.getUserName());
        if (!optionalCommerce.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Commerce commerce = optionalCommerce.get();

        String uuid = UUID.randomUUID().toString();

        commerce.setResetPasswordKey(uuid);
        commerce.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
        commerceRepository.save(commerce);

        String email = parameter.getUserId();
        String subject = "비밀번호 초기화 메일 입니다.";
        String text = "<p>비밀번호 초기화 메일 입니다.</p><p>아래 링크를 클릭하셔서 비밀번호를 초기화 해주세요.</P>"
                + "<div><a target='_blank' href='http://localhost:8080/commerce/reset/password?id=" + uuid + "'>비밀번호 초기화 링크</a></div>";
        mailComponents.sendMail(email, subject, text);

        return true;
    }

    @Override
    public boolean resetPassword(String uuid, String password) {

        Optional<Commerce> optionalCommerce = commerceRepository.findByResetPasswordKey(uuid);
        if (!optionalCommerce.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Commerce commerce = optionalCommerce.get();

        // 초기화 날짜가 유효한지 체크
        if (commerce.getResetPasswordLimitDt() == null) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        if (commerce.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        commerce.setPassword(encPassword);
        commerce.setResetPasswordLimitDt(null);
        commerce.setResetPasswordKey("");
        commerceRepository.save(commerce);

        return true;
    }

    @Override
    public boolean checkResetPassword(String uuid) {

        Optional<Commerce> optionalCommerce = commerceRepository.findByResetPasswordKey(uuid);
        if (!optionalCommerce.isPresent()) {
            return false;
        }

        Commerce commerce = optionalCommerce.get();

        // 초기화 날짜가 유효한지 체크
        if (commerce.getResetPasswordLimitDt() == null) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        if (commerce.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Commerce> optionalCommerce = commerceRepository.findById(username);
        if (!optionalCommerce.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Commerce commerce = optionalCommerce.get();

        if (!commerce.isEmailAuthYn()) {
            throw new CommerceNotEmailAuthException("이메일 활성화 이후에 로그인 해주세요.");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(commerce.getUserId(), commerce.getPassword(), grantedAuthorities);
    }
}
