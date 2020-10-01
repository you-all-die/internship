package com.example.internship.controller.order;

import com.example.internship.controller.product.ProductController;
import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.outlet.OutletDto;
import com.example.internship.refactoringdto.AddressDto;
import com.example.internship.service.OutletService;
import com.example.internship.service.address.AddressService;
import com.example.internship.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(OrderController.BASE_MAPPING)
@RequiredArgsConstructor
public class OrderController {

    public static final String BASE_MAPPING = "/order";

    private static final String ENTER_MAPPING = "/enter";
    private static final String SHIPPING_MAPPING = "/shipping";
    private static final String PAYMENT_MAPPING = "/payment";
    private static final String CONFIRM_MAPPING = "/confirm";

    private static final String TEMPLATE_DIR = "/order";

    private static final String ENTER_TEMPLATE = TEMPLATE_DIR + "/enter";
    private static final String SHIPPING_TEMPLATE = TEMPLATE_DIR + "/shipping";
    private static final String PAYMENT_TEMPLATE = TEMPLATE_DIR + "/payment";
    private static final String CONFIRM_TEMPLATE = TEMPLATE_DIR + "/confirm";

    private final CustomerService customerService;
    private final AddressService addressService;
    private final OutletService outletService;

    /**
     * Покупатель регистрируется или входит в систему.
     */
    @GetMapping(ENTER_MAPPING)
    public String enterToSystem(
            Authentication authentication,
            Model model
    ) {
        if (null != authentication && authentication.isAuthenticated()) {
            final Optional<CustomerDto> optionalCustomerDto = customerService.getFromAuthentication(authentication);
            model.addAttribute("customer", optionalCustomerDto.orElse(new CustomerDto()));
        } else {
            model.addAttribute("customer", new CustomerDto());
        }
        return ENTER_TEMPLATE;
    }

    @PostMapping(ENTER_MAPPING)
    public String submitCustomerData() {
        return "redirect:" + BASE_MAPPING + SHIPPING_MAPPING;
    }

    /**
     * Покупатель выбирает способ доставки заказа и, если надо, адрес.
     */
    @GetMapping(SHIPPING_MAPPING)
    public String chooseShippingMethod(
            Authentication authentication,
            Model model
    ) {
        model.addAttribute("addresses", Collections.emptyList());
        if (null != authentication && authentication.isAuthenticated()) {
            final Optional<CustomerDto> customer = customerService.getFromAuthentication(authentication);
            if (customer.isPresent()) {
                final List<AddressDto> addresses = addressService.getAllByCustomerId(customer.get().getId());
                model.addAttribute("addresses", addresses);
            }
        }
        final List<OutletDto.Response.Full> outlets = outletService.getOutlets();
        model.addAttribute("outlets", outlets);
        return SHIPPING_TEMPLATE;
    }

    @PostMapping(SHIPPING_MAPPING)
    public String submitShippingMethod() {
        return "redirect:" + BASE_MAPPING + PAYMENT_MAPPING;
    }

    /**
     * Покупатель выбирает способ оплаты заказа и вводит данные карты, если необходимо.
     */
    @GetMapping(PAYMENT_MAPPING)
    public String choosePaymentMethod() {
        return PAYMENT_TEMPLATE;
    }

    @PostMapping(PAYMENT_MAPPING)
    public String submitPaymentMethod() {
        return "redirect:" + BASE_MAPPING + CONFIRM_MAPPING;
    }

    /**
     * Покупатель проверяет данные заказа и подтверждает его.
     */
    @GetMapping(CONFIRM_MAPPING)
    public String confirmOrder() {
        return CONFIRM_TEMPLATE;
    }

    @PostMapping(CONFIRM_MAPPING)
    public String submitOrder() {
        return "redirect:" + ProductController.BASE_URL;
    }
}
