package com.example.internship.api;

import com.example.internship.entity.Customer;
import com.example.internship.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
* @author Romodin Aleksey
*
* Тестирование Rest контроллера CustomerRestController.
*
* Тест: показать данные конкретного пользователя по id
* Тест: показать данные несуществующего пользователя, обработка ошибки 404
* Тест: редактирование данных пользователя
* Тест: редактирование данных пользователя, обработка ошибки 404
 */

public class CustomerRestControllerTest {
    //Макеты
    CustomerService customerService = mock(CustomerService.class);
    Customer customer = mock(Customer.class);
    Customer customerUpdate = mock(Customer.class);
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    //Настройки MockMVC
    @BeforeEach
    public void setup(){
        CustomerRestController customerRestController=new CustomerRestController(customerService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(customerRestController)
                .build();
    }

    //Тест: показать данные конкретного пользователя по id
    @Test
    public void testGetUser() throws Exception {
        //Создание нового пользователя
        customer = createNewCustomer("Петров", "petr@gmail.com");
        //Получение Id для поиска
        long id = customer.getId();

        //Обработка метода, подстановка объекта
        when(customerService.getById(anyLong())).thenReturn(Optional.of(customer));

        //Get запрос: получение данных о пользователе id=1
        mockMvc.perform(get("/api/user/{id}",id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo (print ())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Петр")))
                .andExpect(jsonPath("$.middleName", is("Петрович")))
                .andExpect(jsonPath("$.lastName", is("Петров")))
                .andExpect(jsonPath("$.phone", is("+79271234567")))
                .andExpect(jsonPath("$.email", is("petr@gmail.com")))
                .andExpect(jsonPath("$.password", is("password")));

        //Проверка: сколько раз вызывался каждый метод, больше никаких взаимодействий с сервисом не было.
        verify(customerService, times(2)).getById(1);
        verifyNoMoreInteractions(customerService);
    }


    //Тест: показать данные несуществующего пользователя, обработка ошибки 404
    @Test
    public void testGetUserNotFound() throws Exception{
        //Get запрос: получение данных о пользователе id=1
        mockMvc.perform(get("/api/user/{id}",1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //Проверка: сколько раз вызывался каждый метод, больше никаких взаимодействий с сервисом не было.
        verify(customerService, times(1)).getById(1);
        verifyNoMoreInteractions(customerService);
    }


    //Тест: редактирование данных пользователя
    @Test
    public void testPostUser() throws Exception{
        objectMapper = new ObjectMapper();
        //Создание нового пользователя
        customer = createNewCustomer("Петров", "petr@gmail.com");

        //Получение Id для поиска
        long id = customer.getId();

        //Создание нового пользователя с измененными данными
        //Изменяем фамилию и E-mail
        customerUpdate = createNewCustomer("Иванов", "petrivanov@gmail.com");

        //Обработка метода, подстановка объекта
        when(customerService.getById(id)).thenReturn(Optional.of(customer));
        doNothing().when(customerService).save(customer);

        //Put запрос: внесение изменений в данные пользователя
        mockMvc.perform(put("/api/user/{id}",id)
                .content(objectMapper.writeValueAsString(customerUpdate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //Get запрос: проверка внесенных изменений
        mockMvc.perform(get("/api/user/{id}",id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int)id)))
                .andExpect(jsonPath("$.firstName", is("Петр")))
                .andExpect(jsonPath("$.middleName", is("Петрович")))
                .andExpect(jsonPath("$.lastName", is(customerUpdate.getLastName())))
                .andExpect(jsonPath("$.phone", is("+79271234567")))
                .andExpect(jsonPath("$.email", is(customerUpdate.getEmail())))
                .andExpect(jsonPath("$.password", is("password")));

        //Проверка: сколько раз вызывался каждый метод, больше никаких взаимодействий с сервисом не было.
        verify(customerService, times(3)).getById(id);
        verify(customerService, times(1)).save(customer);
        verifyNoMoreInteractions(customerService);
    }

    //Тест: редактирование данных пользователя, обработка ошибки 404
    @Test
    public void testPostUserNotFound() throws Exception{
        objectMapper = new ObjectMapper();
        //Создание нового пользователя
        customer = createNewCustomer("Петров", "petr@gmail.com");

        //Получение Id для поиска
        long id = customer.getId();

        //Создание нового пользователя с измененными данными
        //Изменяем фамилию и E-mail
        customerUpdate = createNewCustomer("Иванов", "petrivanov@gmail.com");

        //Обработка метода, подстановка объекта
        when(customerService.getById(id)).thenReturn(Optional.empty());
        doNothing().when(customerService).save(customer);

        //Put запрос: внесение изменений в данные пользователя
        mockMvc.perform(put("/api/user/{id}",id)
                .content(objectMapper.writeValueAsString(customerUpdate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //Проверка: сколько раз вызывался каждый метод, больше никаких взаимодействий с сервисом не было.
        verify(customerService, times(1)).getById(id);
        verifyNoMoreInteractions(customerService);
    }


    private Customer createNewCustomer(String lastName, String email){
        customer = new Customer();
        customer.setId((long) 1);
        customer.setFirstName("Петр");
        customer.setMiddleName("Петрович");
        customer.setLastName(lastName);
        customer.setPhone("+79271234567");
        customer.setEmail(email);
        customer.setPassword("password");
        return customer;
    }

}
