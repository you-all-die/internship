package com.example.internship.controller.address;

import com.example.internship.controller.customer.CustomerController;
import com.example.internship.refactoringdto.AddressDto;
import com.example.internship.service.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping(AddressController.BASE_MAPPING)
@RequiredArgsConstructor
public class AddressController {

    public static final String BASE_MAPPING = "/address";

    private final AddressService addressService;

    @GetMapping("/add")
    public String addShippingAddress(
            Authentication authentication,
            Model model
    ) {
        if (authentication.isAuthenticated()) {
            model.addAttribute("address", new AddressDto());
            return "/address/edit";
        } else {
            throw new EntityNotFoundException("Customer not found");
        }
    }

    @GetMapping("/{addressId}/edit")
    public String editShippingAddress(
            @PathVariable @NotNull Long addressId,
            Model model
    ) {
        final AddressDto addressDto = addressService.getById(addressId);
        model.addAttribute("address", addressDto);
        return "/address/edit";
    }

    @PostMapping("/save")
    public String saveShippingAddress(
            @ModelAttribute AddressDto addressDto
    ) {
        addressService.save(addressDto);
        return "redirect:" + CustomerController.BASE_MAPPING;
    }

    @GetMapping("/{addressId}/delete")
    public String deleteShippingAddress(
            @PathVariable @NotNull Long addressId
    ) {
        addressService.deleteById(addressId);
        return "redirect:" + CustomerController.BASE_MAPPING;
    }
}
