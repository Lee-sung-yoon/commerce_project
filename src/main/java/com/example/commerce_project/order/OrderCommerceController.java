package com.example.commerce_project.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderCommerceController {

    @GetMapping("/order/commerce/list.do")
    public String list() {



        return "order/commerce/list";
    }
}
