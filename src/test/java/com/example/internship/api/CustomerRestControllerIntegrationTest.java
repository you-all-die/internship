//package com.example.internship.api;
//TODO: Рефакторинг по время тестов для сервисов
//
//import com.example.internship.entity.Customer;
//import com.example.internship.service.customer.CustomerService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.hamcrest.Matchers.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///*
// *@author Romodin Aleksey
// *
// * Интеграционное тестирование CustomerRestController:
// * Тест: поиск пользователей с незаданными параметрами, размер и номер страницы по умолчанию.
// * Тест: поиск пользователей с заданными параметрами, размер страницы 10 и номер страницы по умолчанию.
// * Тест: поиск пользователей с заданными параметрами, вывод второй страницы.
// * Тест: поиск пользователей с заданными параметрами(фамилия).
// * Тест: поиск пользователей с заданными параметрами(имя, фамилия).
// * Тест: поиск пользователей с заданными параметрами(имя, фамилия, почта).
// *
// */
//
//@TestPropertySource(locations = "classpath:test.properties")
//@SpringBootTest
//@AutoConfigureMockMvc
//public class CustomerRestControllerIntegrationTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private  CustomerService customerService;
//
//    private Customer customerFirst;
//    private Customer customerSecond;
//    private Customer customerThird;
//
//
//    @BeforeEach
//    public void beforeEach(){
//        customerService.deleteAll();
//        customerFirst = createNewCustomer("Иванов","ivanov@mail.ru");
//        customerSecond = createNewCustomer("Петров","petrov@mail.ru");
//        customerThird = createNewCustomer("Сидоров","sidorov@mail.ru");
//    }
//
//    //Тест: поиск пользователей с незаданными параметрами, размер и номер страницы по умолчанию
//    @Test
//    public void testSearchUserNotParam() throws Exception{
//        customerService.deleteAll();
//        //Get запрос: поиск с пустыми параметрами
//        mockMvc.perform(get("/api/user/search")
//                .param("pageNumber", "0")
//                .param("pageSize", "20")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo (print ())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.pageNumber", is(0)))
//                .andExpect(jsonPath("$.pageSize", is(20)))
//                .andExpect(jsonPath("$.totalCustomers", is(0)));
//    }
//
//
//    /*Тест: поиск пользователей с заданными параметрами, размер страницы 10 и номер страницы по умолчанию.
//     *  -Добавили трех новых пользователей
//     */
//    @Test
//    public void testSearchUserManyUsers() throws Exception{
//        //Get запрос: поиск с пустыми параметрами
//        mockMvc.perform(get("/api/user/search")
//                .param("pageNumber", "0")
//                .param("pageSize", "10")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo (print ())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.pageNumber", is(0)))
//                .andExpect(jsonPath("$.pageSize", is(10)))
//                .andExpect(jsonPath("$.totalCustomers", is(3)))
//                .andExpect(jsonPath("$.customers", hasSize(3)))
//                .andExpect(jsonPath("$.customers.[0].id", is(customerFirst.getId().intValue())))
//                .andExpect(jsonPath("$.customers.[1].firstName", is(customerSecond.getFirstName())))
//                .andExpect(jsonPath("$.customers.[2].email", is(customerThird.getEmail())));
//    }
//
//
//    /*Тест: поиск пользователей с заданными параметрами, вывод второй страницы.
//     * -Добавили трех новых пользователей
//     * -Указали рамер страницы 20
//     * -Выбрали вторую страницу для вывода
//    */
//    @Test
//    public void testSearchUserManyUsersPageOne() throws Exception{
//        //Get запрос: вывод второй страницы
//        mockMvc.perform(get("/api/user/search")
//                .param("pageNumber", "1")
//                .param("pageSize", "20")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo (print ())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.pageNumber", is(1)))
//                .andExpect(jsonPath("$.pageSize", is(20)))
//                .andExpect(jsonPath("$.totalCustomers", is(3)))
//                .andExpect(jsonPath("$.customers", hasSize(0)));
//    }
//
//
//    /*Тест: поиск пользователей с заданными параметрами(фамилия).
//     * -Добавили трех новых пользователей
//     * -Указали рамер страницы 20
//     * -Добавили поиск по фамилии "Иванов"
//     */
//    @Test
//    public void testSearchUserManyUsersParamLastName() throws Exception{
//        //Get запрос: поиск по Фамилии
//        mockMvc.perform(get("/api/user/search")
//                .param("pageNumber", "0")
//                .param("pageSize", "20")
//                .param("lastName", "Иванов")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo (print ())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.pageNumber", is(0)))
//                .andExpect(jsonPath("$.pageSize", is(20)))
//                .andExpect(jsonPath("$.totalCustomers", is(1)))
//                .andExpect(jsonPath("$.customers", hasSize(1)))
//                .andExpect(jsonPath("$.customers[0].lastName", is(customerFirst.getLastName())))
//                .andExpect(jsonPath("$.customers[0].id", is(customerFirst.getId().intValue())));
//    }
//
//
//    /*Тест: поиск пользователей с заданными параметрами(имя, фамилия).
//     * -Добавили трех новых пользователей
//     * -Указали рамер страницы 20
//     * -Добавили поиск по имени "Петр"
//     * -Добавили поиск по фамилии "Иванов"
//     */
//    @Test
//    public void testSearchUserManyUsersParamFirstNameParamLastName() throws Exception{
//        //Get запрос: поиск имени и фамилии
//        mockMvc.perform(get("/api/user/search")
//                .param("pageNumber", "0")
//                .param("pageSize", "20")
//                .param("firstName", "Петр")
//                .param("lastName", "Иванов")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo (print ())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.pageNumber", is(0)))
//                .andExpect(jsonPath("$.pageSize", is(20)))
//                .andExpect(jsonPath("$.totalCustomers", is(1)))
//                .andExpect(jsonPath("$.customers", hasSize(1)))
//                .andExpect(jsonPath("$.customers[0].firstName", is(customerFirst.getFirstName())))
//                .andExpect(jsonPath("$.customers[0].lastName", is(customerFirst.getLastName())))
//                .andExpect(jsonPath("$.customers[0].id", is(customerFirst.getId().intValue())));
//    }
//
//
//    /*Тест: поиск пользователей с заданными параметрами(имя, фамилия, почта).
//     * -Добавили трех новых пользователей
//     * -Указали рамер страницы 20
//     * -Добавили поиск по имени "Петр"
//     * -Добавили поиск по фамилии "иван"
//     * -Добавили поиск по почте "@mail.ru"
//     */
//    @Test
//    public void testSearchUserManyUsersParamFirstNameParamLastNameParamEmail() throws Exception{
//        //Get запрос: поиск по имени, фамилии, почте
//        mockMvc.perform(get("/api/user/search")
//                .param("pageNumber", "0")
//                .param("pageSize", "20")
//                .param("firstName", "Петр")
//                .param("lastName", "иван")
//                .param("email", "@mail.ru")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo (print ())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.pageNumber", is(0)))
//                .andExpect(jsonPath("$.pageSize", is(20)))
//                .andExpect(jsonPath("$.totalCustomers", is(1)))
//                .andExpect(jsonPath("$.customers", hasSize(1)))
//                .andExpect(jsonPath("$.customers[0].firstName", is(customerFirst.getFirstName())))
//                .andExpect(jsonPath("$.customers[0].email", is(customerFirst.getEmail())))
//                .andExpect(jsonPath("$.customers[0].lastName", is(customerFirst.getLastName())))
//                .andExpect(jsonPath("$.customers[0].id", is(customerFirst.getId().intValue())));
//    }
//
//    //Метод добавления новых пользователей
//    private Customer createNewCustomer(String lastName, String email){
//        Customer customer = new Customer();
//        customer.setFirstName("Петр");
//        customer.setMiddleName("Петрович");
//        customer.setLastName(lastName);
//        customer.setPhone("+79271234567");
//        customer.setEmail(email);
//        customer.setPassword("password");
//        customerService.save(customer);
//        return customer;
//    }
//}
