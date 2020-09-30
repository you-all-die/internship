package com.example.internship.service.address;

import com.example.internship.entity.Address;
import com.example.internship.refactoringdto.AddressDto;

import java.util.List;

/**
 * @author Роман Каравашкин
 * <p>
 * Refactoring by Ivan Gubanov 25.09.20
 */

public interface AddressService {

    /**
     * Возвращает все адреса покупателя.
     *
     * @param customerId идентификатор покупателя
     * @return возвращает список всех адресов покупателя, в случае неудачи null
     */
    List<AddressDto> getAllByCustomerId(Long customerId);

    /**
     * Добавляет новый адрес для покупателя.
     *
     * @param customerId идентификатор покупателя
     * @param addressDto новый адрес
     * @return возвращает добавленный адрес, в случае неудачи null
     */
    AddressDto addAddressToCustomer(Long customerId, AddressDto addressDto);

    /**
     * Удаляет адрес у покупателя.
     *
     * @param customerId идентификатор покупателя
     * @param addressId  идентификатор адреса
     * @return true если адрес удален иначе false
     */
    boolean deleteAddressFromCustomerByIds(Long customerId, Long addressId);

    /**
     * Возвращает адрес по его идентификатору и идентификатору покупателя.
     *
     * @param customerId идентификатор покупателя
     * @param addressId  идентификатор адреса
     * @return возвращает адрес, в случае неудачи null
     */
    AddressDto getAddressFromCustomerByIds(Long customerId, Long addressId);

    Address save(AddressDto addressDto);
}
