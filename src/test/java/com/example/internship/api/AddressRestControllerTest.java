package com.example.internship.api;

import com.example.internship.refactoringdto.AddressDto;
import com.example.internship.service.address.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Роман Каравашкин
 * <p>
 * Refactoring by Ivan Gubanov 25.09.20
 */

public class AddressRestControllerTest {

    private static final AddressService ADDRESS_SERVICE = mock(AddressService.class);

    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new AddressRestController(ADDRESS_SERVICE)).build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final AddressDto ADDRESS = new AddressDto();

    private static final AddressDto BAD_ADDRESS = new AddressDto();

    @BeforeAll
    public static void beforeAll() {

        ADDRESS.setCustomerId(12L);
        ADDRESS.setId(1L);
        ADDRESS.setRegion("Region");
        ADDRESS.setDistrict("District");
        ADDRESS.setCity("City");
        ADDRESS.setStreet("Street");
        ADDRESS.setHouse("House");
        ADDRESS.setApartment("Apartment");
        ADDRESS.setComment("Comment");

        BAD_ADDRESS.setCustomerId(0L);

        when(ADDRESS_SERVICE.getAllByCustomerId(ADDRESS.getCustomerId())).thenReturn(List.of(ADDRESS));
        when(ADDRESS_SERVICE.getAllByCustomerId(0L)).thenReturn(null);
        when(ADDRESS_SERVICE.addAddressToCustomer(ADDRESS.getCustomerId(), ADDRESS)).thenReturn(ADDRESS);
        when(ADDRESS_SERVICE.addAddressToCustomer(BAD_ADDRESS.getCustomerId(), BAD_ADDRESS)).thenReturn(null);
        when(ADDRESS_SERVICE.deleteAddressFromCustomerByIds(ADDRESS.getCustomerId(), ADDRESS.getId())).thenReturn(true);
        when(ADDRESS_SERVICE.deleteAddressFromCustomerByIds(0L, 0L)).thenReturn(false);
    }

    /**
     * Тест успешного получения списка всех адресов покупателя.
     *
     * @throws Exception mockMvc.perform
     */
    @Test
    public void getAllAddressesByCustomerIdSucessTest() throws Exception {

        mockMvc.perform(get("/api/customers/{customerId}/addresses", ADDRESS.getCustomerId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].customerId", is(12)))
                .andExpect(jsonPath("$[0].region", is("Region")))
                .andExpect(jsonPath("$[0].city", is("City")))
                .andExpect(jsonPath("$[0].district", is("District")))
                .andExpect(jsonPath("$[0].street", is("Street")))
                .andExpect(jsonPath("$[0].house", is("House")))
                .andExpect(jsonPath("$[0].apartment", is("Apartment")))
                .andExpect(jsonPath("$[0].comment", is("Comment")));

        verify(ADDRESS_SERVICE, times(1)).getAllByCustomerId(ADDRESS.getCustomerId());
    }

    /**
     * Тест получения списка всех адресов для несуществующего покупателя.
     *
     * @throws Exception mockMvc.perform
     */
    @Test
    public void getAllAddressesByCustomerIdNotFoundTest() throws Exception {

        mockMvc.perform(get("/api/customers/{customerId}/addresses", 0)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(ADDRESS_SERVICE, times(1)).getAllByCustomerId(0L);
    }

    /**
     * Тест успешного добавления нового адреса для покупателя.
     *
     * @throws Exception mockMvc.perform
     */
    @Test
    public void addAddressToCustomerSuccessTest() throws Exception {

        mockMvc.perform(post("/api/customers/{customerId}/addresses", ADDRESS.getCustomerId())
                .content(objectMapper.writeValueAsString(ADDRESS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Тест добавления нового адреса для несуществующего покупателя.
     *
     * @throws Exception mockMvc.perform
     */
    @Test
    public void addAddressToCustomerErrorTest() throws Exception {

        mockMvc.perform(post("/api/customers/{customerId}/addresses", 0L)
                .content(objectMapper.writeValueAsString(BAD_ADDRESS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Тест удаления адреса у покупателя.
     *
     * @throws Exception mockMvc.perform
     */
    @Test
    public void deleteAddressFromCustomerByIdsSuccessTest() throws Exception {
        mockMvc.perform(delete("/api/customers/{customerId}/addresses/{addressId}",
                ADDRESS.getCustomerId(), ADDRESS.getId())
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Тест удаления адреса у несуществующего покупателя.
     *
     * @throws Exception mockMvc.perform
     */
    @Test
    public void deleteAddressFromCustomerByIdsErrorTest() throws Exception {
        mockMvc.perform(delete("/api/customers/{customerId}/addresses/{addressId}", 0L, 0L)
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}



