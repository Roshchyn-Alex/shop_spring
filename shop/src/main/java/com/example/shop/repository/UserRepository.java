package com.example.shop.repository;

import com.example.shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<User, Long> - укзываем молдель и тип данных, с которыми работаем
public interface UserRepository extends JpaRepository<User, Long> {
//    описываем метод findByUsername для условия (нельзя созд с одинков логином)
    User findByUsername(String username);
}
