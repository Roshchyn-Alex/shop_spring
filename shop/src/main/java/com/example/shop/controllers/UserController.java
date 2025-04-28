package com.example.shop.controllers;

import com.example.shop.models.Role;
import com.example.shop.models.User;
import com.example.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController {

//    Для сохранения пользователя при регистрации
    @Autowired
    private UserRepository userRepository;

//    кешируем пароль
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }
//    страница личного кабинета
    @GetMapping("/user")
    public String userProfile(Principal principal, Model model) {
//        получаем данные пользователя из БД
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
            if (user != null) {
                model.addAttribute("email", user.getEmail());
            }
        return "user";
    }

//    обновление данных пользователя через форму userForm
    @PostMapping("/user/update")
    public String updateUser(Principal principal, User userForm) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setEmail(userForm.getEmail());
            user.setRoles(userForm.getRoles());
            user.setPassword(passwordEncoder.encode(userForm.getPassword()));
            userRepository.save(user);
        }
        return "redirect:/user";
    }

//       обновление данных пользователя, используя @RequestParam (более сложный вариант)
//    @PostMapping("/user/update")
//    public String updateUser(Principal principal,
//                             @RequestParam String email,
//                             @RequestParam String password,
////                             принимаем роли как Set<String>
//                             @RequestParam Set<String> roles) {
//
//        String username = principal.getName();
//        User user = userRepository.findByUsername(username);
//            if (user != null) {
//                user.setEmail(email);
//                user.setPassword(passwordEncoder.encode(password));
//                //  преобразуем роли из строк в enum
//                Set<Role> roleEnums = roles.stream()
//                        .map(Role::valueOf)
//                 //  преобразуем строку в enum
//                        .collect(Collectors.toSet());
//                //  обновляем   роли
//                user.setRoles(roleEnums);
//                userRepository.save(user);
//            }
//            return "redirect:/user";
//    }

//    в скобках описываем error
//    name - отслеживания такого парметра, как  error
//    defaultValue - пустая строка
//    required = false - этот парметр необязателен, те страница должны быть доступна
    @GetMapping("/reg")
    public String reg(@RequestParam(name = "error", defaultValue = "", required = false) String error, Model model) {
//        проверяем на совпадение
        if(error.equals("username")) {
            model.addAttribute("error", "Такой логин уже занят");
        }
        return "reg";
    }

//    обрабатывем данные полученные из формы регистрации пользователя
    @PostMapping("/reg")
    public String addUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password) {
//        добавляем условие, что нельзя создаь пользователя с одинковым именем
//        findByUsername этот метод прописываем в UserRepository
        if(userRepository.findByUsername(username) != null) {
//            если нашли, то делаем
            return "redirect:/reg?error=username";
        }
        password = passwordEncoder.encode(password);
//        Создаем объект на основе класса User и передаем ему параметры согласно конструктора
        User user = new User(username, password, email, true, Collections.singleton(Role.ADMIN));
        userRepository.save(user);
//        переадресовавыем пользователя на страницу авторизации
        return "redirect:/login";
    }
}
