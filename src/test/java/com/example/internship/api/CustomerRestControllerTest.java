package com.example.internship.api;

import com.example.internship.entity.Customer;
import com.example.internship.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
*@author Romodin Aleksey
* Тестирование Rest контроллера CustomerRestController
*
 */


public class CustomerRestControllerTest {
    //Макеты
    CustomerService customerService = mock(CustomerService.class);
    Customer customer = mock(Customer.class);
    CustomerRestController customerRestController=mock(CustomerRestController.class);

    private MockMvc mockMvc;

    //Настройки MockMVC
    @BeforeEach
    public void setup(){
        customerRestController=new CustomerRestController(customerService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(customerRestController)
                .build();
    }


    //Показать данные конкретного пользователя
    @Test
    public void testGetUser() throws Exception {
        //Добавление нового пользователя
        customer = new Customer();
        customer.setId((long) 1);
        customer.setFirstName("Петр");
        customer.setMiddleName("Петрович");
        customer.setLastName("Петров");
        customer.setPhone("+79271234567");
        customer.setEmail("petr@gmail.com");

        //Получение Id для поиска
        long id = customer.getId();

        //Обработка метода, подстановка объекта
        Mockito.when(customerService.getById(Mockito.anyLong())).thenReturn(Optional.of(customer));

        //Get запрос
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/{id}",id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Петр")))
                .andExpect(jsonPath("$.middleName", is("Петрович")))
                .andExpect(jsonPath("$.lastName", is("Петров")))
                .andExpect(jsonPath("$.phone", is("+79271234567")))
                .andExpect(jsonPath("$.email", is("petr@gmail.com")));
    }

}
