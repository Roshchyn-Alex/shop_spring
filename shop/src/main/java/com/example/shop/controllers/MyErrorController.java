package com.example.shop.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

//    @RequestMapping для обработки ошибок
    @RequestMapping("/error")
    public String eror() {
        return "error";
    }
    // контроллер для отслеживания страницы Доступ запрещен
//    @RequestMapping("/access-denied")
//    public String accessDenied() {
//        return "access-denied";
//    }
}

