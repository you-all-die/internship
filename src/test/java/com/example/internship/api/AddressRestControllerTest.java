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
    static AddressDto addressDtoOne = mock(AddressDto.class);
    static AddressDto addressDtoTwo = mock(AddressDto.class);

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        AddressRestController addressRestController = new AddressRestController(addressService);
        mockMvc = MockMvcBuilders.standaloneSetup(addressRestController).build();
    }

    @Test
    public void testGetAddressByUserId() throws Exception {
        objectMapper = new ObjectMapper();
        addressDtoOne = createNewAddressDto(1L,2L, "Mordoviya");
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
    }

    @Test
    public void testPostAddress() throws Exception {
        objectMapper = new ObjectMapper();
        addressDtoOne = createNewAddressDto(1L,2L, "Miami");

        final List<AddressDto> addressDtoList = List.of(addressDtoOne);
        Long customerId = addressDtoOne.getCustomerId();

        when(addressService.getAllById(customerId)).thenReturn(addressDtoList);
        doNothing().when(addressService).addAddress(addressDtoOne);

        mockMvc.perform(post("/api/user/{id}/address", customerId)
                .content(objectMapper.writeValueAsString(addressDtoOne))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/user/{id}/address", customerId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].customerId", is(2)))
                .andExpect(jsonPath("$[0].region", is("Miami")))
                .andExpect(jsonPath("$[0].city", is("City1")))
                .andExpect(jsonPath("$[0].district", is("district1")))
                .andExpect(jsonPath("$[0].street", is("street1")))
                .andExpect(jsonPath("$[0].house", is("1")))
                .andExpect(jsonPath("$[0].apartment", is("11")))
                .andExpect(jsonPath("$[0].comment", is("test1")))
                .andReturn();

    }

    @Test
    public void testDeleteAddress() throws Exception{
        objectMapper = new ObjectMapper();
        addressDtoOne = createNewAddressDto(1L,2L,"Mexico");
        addressDtoTwo = createNewAddressDto(2L,2L,"Texas");

        final List<AddressDto> addressDtoList = List.of(addressDtoOne,addressDtoTwo);

        Long customerId = addressDtoOne.getCustomerId();
        Long id = addressDtoOne.getId();

        when(addressService.getAllById(customerId)).thenReturn(addressDtoList);
        when(addressService.deleteAddress(customerId,id)).thenReturn(addressDtoList);
        doNothing().when(addressService).addAddress(addressDtoOne);
        doNothing().when(addressService).addAddress(addressDtoTwo);
        //doNothing().when(addressService).deleteAddress(customerId,id);

        mockMvc.perform(post("/api/user/{id}/address", customerId)
                .content(objectMapper.writeValueAsString(addressDtoOne))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        mockMvc.perform(post("/api/user/{id}/address", customerId)
                .content(objectMapper.writeValueAsString(addressDtoTwo))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(get("/api/user/{id}/address", customerId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].customerId", is(2)))
                .andExpect(jsonPath("$[0].region", is("Mexico")))
                .andExpect(jsonPath("$[0].city", is("City1")))
                .andExpect(jsonPath("$[0].district", is("district1")))
                .andExpect(jsonPath("$[0].street", is("street1")))
                .andExpect(jsonPath("$[0].house", is("1")))
                .andExpect(jsonPath("$[0].apartment", is("11")))
                .andExpect(jsonPath("$[0].comment", is("test1")))

                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].customerId", is(2)))
                .andExpect(jsonPath("$[1].region", is("Texas")))
                .andExpect(jsonPath("$[1].city", is("City1")))
                .andExpect(jsonPath("$[1].district", is("district1")))
                .andExpect(jsonPath("$[1].street", is("street1")))
                .andExpect(jsonPath("$[1].house", is("1")))
                .andExpect(jsonPath("$[1].apartment", is("11")))
                .andExpect(jsonPath("$[1].comment", is("test1")))
                .andReturn();

        mockMvc.perform(delete("/api/user/{id}/address/{addressId}", customerId,id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].customerId", is(2)))
                .andExpect(jsonPath("$[0].region", is("Texas")))
                .andExpect(jsonPath("$[0].city", is("City1")))
                .andExpect(jsonPath("$[0].district", is("district1")))
                .andExpect(jsonPath("$[0].street", is("street1")))
                .andExpect(jsonPath("$[0].house", is("1")))
                .andExpect(jsonPath("$[0].apartment", is("11")))
                .andExpect(jsonPath("$[0].comment", is("test1")))
                .andReturn();



    }

    private static AddressDto createNewAddressDto(Long id,Long customerId, String region) {
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



