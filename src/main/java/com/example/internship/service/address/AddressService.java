package com.example.internship.service.address;


import com.example.internship.refactoringdto.AddressDto;

import java.util.List;

/**
 * @author Роман Каравашкин
 */

public interface AddressService {

    /**
     * Возвращает все адреса покупателя.
     *
     * @param id идентификатор покупателя
     * @return возвращает список всех адресов покупателя
     */
    List<AddressDto> getAllById(Long id);

    /**
     * Добавляет новый адрес для покупателя.
     *
     * @param addressDto новый адрес
     * @return возвращает добавленный адрес
     */
    AddressDto addAddress(AddressDto addressDto);

    /**
     * Удаляет адрес у покупателя.
     *
     * @param id        идентификатор покупателя
     * @param addressId идентификатор адреса
     * @return true если адрес удален иначе false
     */
    boolean deleteAddress(Long id, Long addressId);
}
