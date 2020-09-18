package com.example.internship.service.item;

import com.example.internship.entity.Item;
import com.example.internship.entity.Order;
import com.example.internship.entity.OrderLine;
import com.example.internship.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Lapshin
 */

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;

    @Override
    public boolean makeItem(OrderLine orderLine, Order savedOrder) {
        Item item = new Item();

        item.setOrder(savedOrder);
        item.setItemCategoryId(orderLine.getProduct().getCategory().getId());
        item.setItemDescription(orderLine.getProduct().getDescription());
        item.setItemName(orderLine.getProduct().getName());
        item.setItemPicture(orderLine.getProduct().getPicture());
        item.setItemPrice(orderLine.getProduct().getPrice());
        item.setItemQuantity(orderLine.getProductQuantity());

        itemRepository.save(item);
        return true;
    }
}
