package com.example.internship.service.item;

import com.example.internship.entity.Item;
import com.example.internship.entity.Order;
import com.example.internship.entity.OrderLine;
import com.example.internship.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;

    @Override
    public boolean makeItem(OrderLine orderLine, Order savedOrder) {
        Item item = new Item();

        item.id = orderLine.getId();
        item.order = savedOrder;
        item.itemCategoryId = orderLine.getProduct().getCategory().getId();
        item.itemDescription = orderLine.getProduct().getDescription();
        item.itemName = orderLine.getProduct().getName();
        item.itemPicture = orderLine.getProduct().getPicture();
        item.itemPrice = orderLine.getProduct().getPrice();
        item.itemQuantity = orderLine.getProductQuantity();

        System.out.println("ITEM TO SAVE: " + item);
        itemRepository.save(item);
        return true;
    }
}
