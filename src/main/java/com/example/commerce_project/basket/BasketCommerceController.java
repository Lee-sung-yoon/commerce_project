package com.example.commerce_project.basket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasketCommerceController {

    @GetMapping("/basket/commerce/list.do")
    public String list() {



        return "basket/commerce/list";
    }
}
