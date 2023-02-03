package com.example.commerce_project.order;

import org.springframework.web.bind.annotation.GetMapping;

public class OrderMainController {

    @GetMapping("/order/main.do")
    public String list() {


        return "order/main";
    }
}
