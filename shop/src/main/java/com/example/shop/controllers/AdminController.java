package com.example.shop.controllers;

import com.example.shop.models.Item;
import com.example.shop.models.User;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.Set;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

//    отслеживание кабинета админа
        @GetMapping("/admin")
    public String admin(Model model) {
            // Получаем всех пользователей на сайте
            Iterable<User> users = userRepository.findAll();
            // Передаем их в шаблон и там они будут отображены
            model.addAttribute("users", users);
            return "admin";
    }

    @GetMapping("/admin/user-{id}")
    public String userReviews(@PathVariable(value = "id") long userId, Model model) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Set<Item> items = user.getItems();
            model.addAttribute("items", items);
            model.addAttribute("user", user);
            return "user-items";
        } else {
            return "redirect:/admin";
        }
    }
}
