package com.example.shop.models;

import jakarta.persistence.*;

// это антотация означает модель, кот описывает наш класс
@Entity
public class Item {
// наши поля Id, title, info, image, price

    @Id
//    автомат генерация
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title, info, image;
    private short price;

//    для добавления автора товара - созд новое поле, кот будет хранить ссылку на нашего пользователя в модели User
//  @JoinColumn(name = "user_id") указываем по какому полю будем связваться
    @JoinColumn(name = "user_id")
//    @OneToOne один автор один товар
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

//    создаем конструтор, который будет принимать данные с формачки по добавлению товара
//    без id, тк Id автоматически, не забываем про пустой конструктор
    public Item() {
    }
// User user для указания автора
    public Item(String title, String info, String image, short price, User user) {
        this.title = title;
        this.info = info;
        this.image = image;
        this.price = price;
        this.user = user;
    }

    //    добавляем геттеры и сеттеры
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public short getPrice() {
        return price;
    }

    public void setPrice(short price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
