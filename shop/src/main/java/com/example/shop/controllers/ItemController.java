package com.example.shop.controllers;

import com.example.shop.models.Item;
import com.example.shop.models.User;
import com.example.shop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// контроллер для редактирования товаров
@Controller
public class ItemController {
//    для сохрания нового добавленного товара
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping ("/item/add")
    public String add() {
//        возвращаем шаблон html, который нужно создать и описать
        return "add-item";
    }

//    описывем сам функционал (получаем данные из формачки для добавления товара)
    @PostMapping("/item/add")
    public String store(
//        при добавлении нового товара (после создания поля для автора) теперь нужно в автомат режиме указывать автора
//        поэтому обращаемся к аннтотации @AuthenticationPrincipal
            @AuthenticationPrincipal User user,
            @RequestParam String title,
            @RequestParam String image,
            @RequestParam String price,
            @RequestParam String info
            ) {
//        создаем объект, в кот передадим все параметры
//        предварительно создаем конструтор в models Item
//        цену приводим к переменной short, тк была  String
//        user для добавления автора, после появления нового поля
        Item item = new Item(title, info, image, Short.parseShort(price), user);
//        сохраняем
        itemRepository.save(item);
//      делаем редирект на главную страницу
        return "redirect:/";
    }
//    прописываем функционал отображения товара по id
//    id -это динамич параметр, который будет изменяться, поэтому в {}
    @GetMapping("/item/{id}")
    public String showItem(@PathVariable(value = "id") long id, Model model) {
//        ищем соотв запись через репозиторий по id
//        orElse(null) условие, если не найден, будем устанавливать пустой объект на основе класса Item
        Item item = itemRepository.findById(id).orElse(new Item());
        model.addAttribute("item", item);
//        передаем внуть шаблона найденный товар
        return "show-item";
    }
//    прописываем ссылку на редактирование товара
    @GetMapping("/item/{id}/update")
    public String update(@PathVariable(value = "id") long id, Model model) {
        Item item = itemRepository.findById(id).orElse(new Item());
        model.addAttribute("item", item);
        return "item-update";
    }
//  метод для обновления товара
    @PostMapping ("/item/{id}/update")
    public String updateItem(
//            сразу получаем id
            @PathVariable(value = "id") long id,
            @RequestParam String title,
            @RequestParam String image,
            @RequestParam String price,
            @RequestParam String info
    ) {
//        нужно сначала найти запись по id
        Item item = itemRepository.findById(id).orElse(new Item());
//        далее принимаем строку и устаналиваем новые параметры
        item.setTitle(title);
        item.setImage(image);
        item.setInfo(info);
        item.setPrice(Short.parseShort(price));
//        обновляем и сохраняем
        itemRepository.save(item);
//      делаем редирект на страницу с товаром
        return "redirect:/item/" + id;
    }
//    удаление товара
    @PostMapping("/item/{id}/delete")
    public String delete(@PathVariable(value = "id") long id) {
        itemRepository.deleteById(id);
        return "redirect:/";
    }

}
