package com.example.shop.models;

import org.springframework.security.core.GrantedAuthority;

// наследуем GrantedAuthority для установления роли в User для привязки автора товара
public enum Role implements GrantedAuthority {
    USER, ADMIN, REDACTOR;

//    переписываем метод через подсказку после наследования
    @Override
    public String getAuthority() {
        return name();
    }
}
