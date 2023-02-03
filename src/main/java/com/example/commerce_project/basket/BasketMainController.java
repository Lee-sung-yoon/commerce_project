package com.example.commerce_project.basket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasketMainController {

    @GetMapping("/basket/main.do")
    public String main() {


        return "basket/main";
    }
}
