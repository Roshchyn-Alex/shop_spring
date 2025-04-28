package com.example.shop.controllers;

import com.example.shop.models.Item;
import com.example.shop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
//    создаем бин (объект)
    @Autowired
    private ItemRepository itemRepository;

//    для отслеживания перехода на страницу прописываем аннтотацию
//    в скобках указываем url адрем, если ничего, то отслежива гл стр
    @GetMapping("/")
//    создаем метод, где прописывем функционал
    public String index(Model model) {
//        укзываем шаблон, кот будет показан
//        для получения всех записей из БД и передачи их нашему шаблону
//        дописываем параметр Model model
//        созд спец класс Iterable, в кот будет записаны все записи и помещ в список items
        Iterable<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "index";
    }
    //    передача переменной, выведет About us
    @GetMapping("/about-us2")
    public String aboutUs2(Model model) {
        model.addAttribute("name", "About us");
        return "about-us2";
    }
//    чтобы передать какое-либо значение внутри шаблона
    @GetMapping("/about-us")
//    для отслеживания по какому-либо пораметру из url адреса @RequestParam
//    в этой аннтотации указыв парметр, кот отслеживаем
//    required = false -если параметра не будет, то все равно выпол корректно
//    defaultValue - значение по умолч, если параметр не будет установлен
//    String userName создаем сам параметр
    public String aboutUs(@RequestParam (name = "userName", required = false, defaultValue = "World") String userName, Model model) {
        model.addAttribute("name", userName);
        return "about";
    }
}
