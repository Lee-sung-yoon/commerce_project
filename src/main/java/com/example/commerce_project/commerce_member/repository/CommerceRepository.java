package com.example.commerce_project.commerce_member.repository;

import com.example.commerce_project.commerce_member.entity.Commerce;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommerceRepository extends JpaRepository<Commerce, String> {

    Optional<Commerce> findByEmailAuthKey(String emailAuthKey);

    Optional<Commerce> findByUserIdAndUserName(String usrId, String userName);
    Optional<Commerce> findByResetPasswordKey(String resetPasswordKey);

}
