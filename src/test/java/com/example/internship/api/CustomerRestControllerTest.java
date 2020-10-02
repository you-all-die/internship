package com.example.internship.api;

import com.example.internship.refactoringdto.CustomerDto;
import com.example.internship.refactoringdto.View;
import com.example.internship.service.customer.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Romodin Aleksey
 * *
 * Refactoring by Danil Movenov 28.09.20
 */

public class CustomerRestControllerTest {

    private static final CustomerService CUSTOMER_SERVICE = mock(CustomerService.class);

    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new CustomerRestController(CUSTOMER_SERVICE)).build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final CustomerDto CUSTOMER = new CustomerDto();

    private static final CustomerDto CUSTOMER_UPDATE = new CustomerDto();

    private static final Long CUSTOMER_ID = 1L;

    private static final Long CUSTOMER_NOT_FOUND = 0L;

    @BeforeAll
    public static void beforeAll() {

        CUSTOMER.setId(CUSTOMER_ID);
        CUSTOMER.setFirstName("Иван");
        CUSTOMER.setMiddleName("Иванович");
        CUSTOMER.setLastName("Иванов");
        CUSTOMER.setPhone("+79271234567");
        CUSTOMER.setEmail("mail@mail.com");
        CUSTOMER.setPassword("password");

        CUSTOMER_UPDATE.setFirstName("Николай");
        CUSTOMER_UPDATE.setMiddleName("Николаевич");
        CUSTOMER_UPDATE.setLastName("Николаев");
        CUSTOMER_UPDATE.setPhone("+72345678912");
        CUSTOMER_UPDATE.setEmail("user@user.com");

        when(CUSTOMER_SERVICE.getByIdRef(CUSTOMER_ID)).thenReturn(CUSTOMER);
        when(CUSTOMER_SERVICE.getByIdRef(CUSTOMER_NOT_FOUND)).thenReturn(null);
        when(CUSTOMER_SERVICE.update(CUSTOMER_ID, CUSTOMER_UPDATE)).thenReturn(CUSTOMER_UPDATE);
        when(CUSTOMER_SERVICE.update(CUSTOMER_NOT_FOUND, CUSTOMER_UPDATE)).thenReturn(null);
    }

    /**
     * Тест успешного получения пользователя.
     *
     * @throws Exception mockMvc.perform
     */
    @Test
    public void getByCustomerByIdSuccessTest() throws Exception {

        mockMvc.perform(get("/api/customers/{CUSTOMER_ID}", CUSTOMER_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writerWithView(View.Public.class).writeValueAsString(CUSTOMER)));

        verify(CUSTOMER_SERVICE, times(1)).getByIdRef(CUSTOMER_ID);
    }

    /**
     * Тест провального получения пользователя.
     *
     * @throws Exception mockMvc.perform
     */
    @Test
    public void getByCustomerByIdFailedTest() throws Exception {

        mockMvc.perform(get("/api/customers/{CUSTOMER_NOT_FOUND}", CUSTOMER_NOT_FOUND)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(CUSTOMER_SERVICE, times(1)).getByIdRef(CUSTOMER_NOT_FOUND);
    }

    /**
     * Тест успешного обновления данных пользователя.
     *
     * @throws Exception mockMvc.perform
     */
    @Test
    public void updateCustomerSuccessTest() throws Exception {

        mockMvc.perform(put("/api/customers/{CUSTOMER_ID}", CUSTOMER_ID)
                .content(objectMapper.writeValueAsString(CUSTOMER_UPDATE))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writerWithView(View.Public.class).writeValueAsString(CUSTOMER_UPDATE)));

        verify(CUSTOMER_SERVICE, times(1)).update(CUSTOMER_ID, CUSTOMER_UPDATE);
    }

    /**
     * Тест провального обновления данных пользователя.
     *
     * @throws Exception mockMvc.perform
     */
    @Test
    public void updateCustomerFailedTest() throws Exception {

        mockMvc.perform(put("/api/customers/{CUSTOMER_NOT_FOUND}", CUSTOMER_NOT_FOUND)
                .content(objectMapper.writeValueAsString(CUSTOMER_UPDATE))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(CUSTOMER_SERVICE, times(1)).update(CUSTOMER_NOT_FOUND, CUSTOMER_UPDATE);
    }
}
