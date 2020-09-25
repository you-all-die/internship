package com.example.internship.service.dadata;

import com.example.internship.dto.dadata.DadataAddressDto;

import java.util.List;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public interface DadataService {

    /**
     * Возвращает список подсказок для адресов.
     *
     * @param query запрос для адреса
     * @return список подсказок
     */
    List<DadataAddressDto.ValueOnly> getAddressSuggestions(String query);
}
