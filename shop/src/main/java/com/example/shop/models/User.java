package com.example.shop.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
// указываем название таблички
@Table(name = "users")
//implements UserDetails - для ссылки на автора товара
public class User implements UserDetails {
//    указываем поля id, username, password, email, role, enabled (активен или нет)

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username, password, email;
    private boolean enabled;

// fetch = FetchType.LAZY -ленивый процесс подгрузки
//    EAGER - данные будут подгружены сразу
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
//     при создании нового пользоват будет указ роль и в какой таблице
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    // Указываем связи между таблицами
// Здесь говорим про то, что у одного пользователя может быть множество записей
// CascadeType - указываем что работаем со всеми записями
// FetchType - указываем что вытягиваем все записи при получении пользователя
// Соединаем это все с таблицей Item по полю "user" (в уроке мы делали это поле и добавляли для него OneToOne)
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "user")
// Помещаем все записи в список (обязательно Set)
    private Set<Item> items = new HashSet<>();

    // Также добавьте геттер и сеттер для этого поля
    public User() {}

    public User(String username, String password, String email, boolean enabled, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }
// добавляем методы после наследования UserDetails через подсказку
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

//    для возвращения роли текущ пользователя
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
