package com.example.shop.config;

import com.example.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

//    для авторизации пользователя
    @Autowired
//    private DataSource dataSource;
//    после создания UserService DataSource не нужен
    private UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        отключаем csrf (но будет уязвимость при пердачи ланных с формы)
//        http.csrf(csrf -> csrf.disable())
//        csrf включаем
        http
//                какие страницы доступны пользователю
                .authorizeHttpRequests((requests) -> requests
//                       страница админ только админу
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
 //             указываем страницы, кот пользователю не доступны, если не авторизован (* - id)
                        .requestMatchers("/item/add", "/item/*/update", "/item/*/delete", "/user").authenticated()
//                        все остальные страницы доступны
                        .requestMatchers("/**").permitAll()
//                        отправка данных из форм доступны будут только для авторизованных пользователей
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
//`     при успешной авторизации показываем кабинет пользователя
                        .defaultSuccessUrl("/user")
                        .permitAll()
                )
//                выход из учетки доступен всем
                .logout((logout) -> logout.permitAll());
//         обработка ошибки доступа страница доступ запрещен
//                .exceptionHandling(ex -> ex
//                        .accessDeniedPage("/access-denied")
//                );
        return http.build();
    }

//    метод для авторизации пользователя
//    переписываем после userService
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
//    это часть кода нужна до userService
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?")
////                для выбора роли из таблицы users выбираем username, а из таблицы user_role выбираем roles
////                изначально выбираем из таблицы users, но объединяем с таблицой user_role
////                по таким полям users.id = user_role.user_id
//                .authoritiesByUsernameQuery("SELECT users.username, user_role.roles FROM users " +
//                        "INNER JOIN user_role ON users.id = user_role.user_id WHERE users.username = ?" );
//    }

//    для кеширования пароля дописывем настройки
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
