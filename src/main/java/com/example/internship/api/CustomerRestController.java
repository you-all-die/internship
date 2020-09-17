package com.example.internship.api;

import com.example.internship.dto.CustomerSearchResult;
import com.example.internship.entity.Customer;
import com.example.internship.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * @author Romodin Aleksey
 */

/*Rest-контроллер для сущности "Пользовалели/Customers"
    http://localhost:8080/swagger-ui/
    Методы:
            - GET /api/user/{id} - получение пользователя по идентификатору
            - PUT /api/user/{id} - редактирование данных
            - GET /api/user/search - поиск пользователей
 */

@AllArgsConstructor
@RestController
@RequestMapping("/api/user/")
@Api( value = "customers")

public class CustomerRestController {
    private final CustomerService customerService;

    //Показать данные конкретного пользователя
    @GetMapping("{id}")
    @ApiOperation(value = "Получение данных пользователя по идентификатору", response = Customer.class)
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        if(customerService.getById(id).isPresent()) {
            return new ResponseEntity<>(customerService.getById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND);
    }

    //Редактирование данных
    @PutMapping("{id}")
    @ApiOperation(value = "Редактирование данных")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Customer customer) {
        Customer customerOld = customerService.getById(id).orElse(null);
        if (customerOld!=null) {
            customerOld.setFirstName(customer.getFirstName());
            customerOld.setMiddleName(customer.getMiddleName());
            customerOld.setLastName(customer.getLastName());
            customerOld.setAddresses(customer.getAddresses());
            customerOld.setCart(customer.getCart());
            customerOld.setEmail(customer.getEmail());
            customerOld.setPassword(customer.getPassword());
            customerOld.setPhone(customer.getPhone());
            customerService.save(customerOld);
            return new ResponseEntity<>("Данные пользователя обновлены!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND);
    }

    //Поиск пользователей
    @GetMapping("search")
    @ApiOperation(value = "Поиск пользователей", response = CustomerSearchResult.class)
    public CustomerSearchResult searchUser(@RequestParam(name = "firstName", required = false)
                                               @ApiParam(value = "Поиск по имени") String firstName,
                                           @RequestParam(name = "middleName", required = false)
                                           @ApiParam(value = "Поиск по отчеству") String middleName,
                                           @RequestParam(name = "lastName", required = false)
                                               @ApiParam(value = "Поиск по фамилии") String lastName,
                                           @RequestParam(name = "email", required = false)
                                               @ApiParam(value = "Поиск по email") String email,
                                           @RequestParam(name = "pageSize", required = false, defaultValue = "20")
                                               @ApiParam(value = "Размер страницы") Integer pageSize,
                                           @RequestParam(name = "pageNumber", required = false, defaultValue = "0")
                                               @ApiParam(value = "Номер страницы") Integer pageNumber) {

        return customerService.search(firstName, middleName, lastName, email, pageSize, pageNumber);

    }
}
