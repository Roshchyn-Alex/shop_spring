package com.example.shop.service;

import com.example.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// для системы авторизации (подставления автора)
@Service
public class UserService implements UserDetailsService {

//    подключаем UserRepository
    @Autowired
    private UserRepository userRepository;

    @Override
//    с помощью этого метода находим пользователя по логину
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        обрашаемся к методу findByUsername
        return userRepository.findByUsername(username);
    }
}
