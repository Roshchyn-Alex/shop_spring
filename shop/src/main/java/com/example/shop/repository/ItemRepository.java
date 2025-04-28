package com.example.shop.repository;

import com.example.shop.models.Item;
import org.springframework.data.repository.CrudRepository;

// наследуем CRUD (create, read, update, delete) указыв нашу модель Item и тип данных нашего id
public interface ItemRepository extends CrudRepository<Item, Long> {
}
