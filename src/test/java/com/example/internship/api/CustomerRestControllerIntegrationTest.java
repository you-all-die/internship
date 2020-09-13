package com.example.internship.api;


import com.example.internship.entity.Customer;
import com.example.internship.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/*
 *@author Romodin Aleksey
 *
 * Интегрированное тестирование CustomerRestController
 * Тест:
 */

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerRestControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private  CustomerService customerService;

    @BeforeEach
    public void beforeEach(){
        customerService.deleteAll();
    }

    //Тест: поиск пользователей с незаданными параметрами, размер и номер страницы по умолчанию
    @Test
    public void testSearchUserNotParam() throws Exception{
        //Get запрос: поиск с пустыми параметрами
        mockMvc.perform(get("/api/user/search")
                .param("pageNumber", "0")
                .param("pageSize", "20")
                .accept(MediaType.APPLICATION_JSON))
                .andDo (print ())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber", is(0)))
                .andExpect(jsonPath("$.pageSize", is(20)))
                .andExpect(jsonPath("$.totalCustomers", is(1)));
    }


    //Тест: поиск пользователей с заданными параметрами, размер страницы 10 и номер страницы по умолчанию.
    //Добавили трех новых пользователей
    @Test
    public void testSearchUserTwoUsers() throws Exception{
        Customer customerFirst = createNewCustomer("Иванов","ivanov@mail.ru");
        Customer customerSecond = createNewCustomer("Петров","petrov@mail.ru");
        Customer customerThird = createNewCustomer("Сидоров","sidorov@mail.ru");

        //Get запрос: поиск с пустыми параметрами
        mockMvc.perform(get("/api/user/search")
                .param("pageNumber", "0")
                .param("pageSize", "10")
                .accept(MediaType.APPLICATION_JSON))
                .andDo (print ())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber", is(0)))
                .andExpect(jsonPath("$.pageSize", is(10)))
                .andExpect(jsonPath("$.totalCustomers", is(4)))
                .andExpect(jsonPath("$.customers.[0].id", is(customerFirst.getId().intValue())))
                .andExpect(jsonPath("$.customers.[1].firstName", is(customerSecond.getFirstName())))
                .andExpect(jsonPath("$.customers.[2].email", is(customerThird.getEmail())));


    }

    private Customer createNewCustomer(String lastName, String email){
        Customer customer = new Customer();
        customer.setFirstName("Петр");
        customer.setMiddleName("Петрович");
        customer.setLastName(lastName);
        customer.setPhone("+79271234567");
        customer.setEmail(email);
        customer.setPassword("password");
        customerService.save(customer);
        return customer;
    }
}
