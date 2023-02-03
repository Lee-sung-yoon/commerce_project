package com.example.commerce_project.commerce_member.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CommerceInput {

    private String userId;
    private String userEmail;
    private String userName;
    private String password;
    private String phone;
}
