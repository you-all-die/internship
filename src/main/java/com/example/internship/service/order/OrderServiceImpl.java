package com.example.internship.service.order;

import com.example.internship.controller.checkout.CheckoutForm;
import com.example.internship.entity.Customer;
import com.example.internship.entity.Item;
import com.example.internship.entity.Order;
import com.example.internship.entity.OrderLine;
import com.example.internship.repository.ItemRepository;
import com.example.internship.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * @author  Sergey Lapshin
 */

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Override
    public boolean makeOrder(Customer customer, CheckoutForm checkoutForm, List<OrderLine> orderLines) {
        Order order = new Order();

//                Обязательные поля (контролируются формой)
        order.setCustomerFirstName(checkoutForm.getFirstName());
        order.setCustomerLastName(checkoutForm.getLastName());
        order.setAddressRegion(checkoutForm.getRegion());
        order.setAddressCity(checkoutForm.getCity());
        order.setAddressStreet(checkoutForm.getStreet());
        order.setAddressHouse(checkoutForm.getHouse());
        order.setAddressApartment(checkoutForm.getApartment());

        //        Необязательные поля
        order.setCustomerMiddleName(checkoutForm.getMiddleName() != null ? checkoutForm.getMiddleName() : "");
        order.setCustomerEmail(checkoutForm.getEmail() != null ? checkoutForm.getEmail() : "");
        order.setCustomerPhone(checkoutForm.getPhone() != null ? checkoutForm.getPhone() : "");
        order.setAddressDistrict(checkoutForm.getDistrict() != null ? checkoutForm.getDistrict() : "");
        order.setAddressComment(checkoutForm.getComment() != null ? checkoutForm.getComment() : "");

        order.setStatus("CREATED");
        order.setCustomerId(customer.getId());

        orderRepository.save(order);

        for (OrderLine orderLine: orderLines) {
            Item item = new Item();

            item.setOrder(order);
            item.setItemCategoryId(orderLine.getProduct().getCategory().getId());
            item.setItemDescription(orderLine.getProduct().getDescription());
            item.setItemName(orderLine.getProduct().getName());
            item.setItemPicture(orderLine.getProduct().getPicture());
            item.setItemPrice(orderLine.getProduct().getPrice());
            item.setItemQuantity(orderLine.getProductQuantity());

            itemRepository.save(item);
        }

        return true;
    }
}
