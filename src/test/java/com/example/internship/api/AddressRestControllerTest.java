package com.example.internship.api;

import com.example.internship.dto.addressDto.AddressDto;
import com.example.internship.service.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Роман Каравашкин
 */

public class AddressRestControllerTest {

    AddressService addressService = mock(AddressService.class);

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp() {
        AddressRestController addressRestController = new AddressRestController(addressService);
        mockMvc = MockMvcBuilders.standaloneSetup(addressRestController).build();
    }


    /**
     * Тест на получения аддреса по CustomerID
     *
     * @throws Exception
     */

    @Test
    public void testGetAddressByUserId() throws Exception {

        AddressDto addressDtoOne = createNewAddressDto(1L, 2L, "Mordoviya");
        final List<AddressDto> addressDtoList = List.of(addressDtoOne);
        Long customerId = addressDtoOne.getCustomerId();

        when(addressService.getAllById(anyLong())).thenReturn(addressDtoList);

        mockMvc.perform(get("/api/user/{id}/address", customerId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].customerId", is(2)))
                .andExpect(jsonPath("$[0].region", is("Mordoviya")))
                .andExpect(jsonPath("$[0].city", is("City1")))
                .andExpect(jsonPath("$[0].district", is("district1")))
                .andExpect(jsonPath("$[0].street", is("street1")))
                .andExpect(jsonPath("$[0].house", is("1")))
                .andExpect(jsonPath("$[0].apartment", is("11")))
                .andExpect(jsonPath("$[0].comment", is("test1")))
                .andReturn();

        verify(addressService, times(1)).getAllById(customerId);

    }

    @Test
    public void testGetNullAddress() throws Exception{

        when(addressService.getAllById(anyLong())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/user/{id}/address",15)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    /**
     * Тест на добавление аддреса по CustomerId
     *
     * @throws Exception
     */
    @Test
    public void testPostAddress() throws Exception {
        AddressDto addressDtoOne = createNewAddressDto(1L, 2L, "Miami");

        final List<AddressDto> addressDtoList = List.of(addressDtoOne);

        Long customerId = addressDtoOne.getCustomerId();

        when(addressService.getAllById(customerId)).thenReturn(addressDtoList);


        mockMvc.perform(post("/api/user/{id}/address", customerId)
                .content(objectMapper.writeValueAsString(addressDtoOne))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(addressService, times(1)).addAddress(addressDtoOne);
    }

    /**
     * Удаление аддреса по id
     *
     * @throws Exception
     */

    @Test
    public void testDeleteAddress() throws Exception {
        AddressDto addressDtoOne = createNewAddressDto(1L, 2L, "Miami");


        Long customerId = addressDtoOne.getCustomerId();
        Long id = addressDtoOne.getId();

        mockMvc.perform(delete("/api/user/{id}/address/{addressId}", customerId, id)
                .content(objectMapper.writeValueAsString(addressDtoOne))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        verify(addressService, times(1)).deleteAddress(customerId, id);
    }

    private static AddressDto createNewAddressDto(Long id, Long customerId, String region) {
        AddressDto addressDtoOne = new AddressDto();
        addressDtoOne.setId(id);
        addressDtoOne.setCustomerId(customerId);
        addressDtoOne.setRegion(region);
        addressDtoOne.setCity("City1");
        addressDtoOne.setDistrict("district1");
        addressDtoOne.setStreet("street1");
        addressDtoOne.setHouse("1");
        addressDtoOne.setApartment("11");
        addressDtoOne.setComment("test1");
        return addressDtoOne;

    }

}



