package com.example.internship.api;

import com.example.internship.entity.Customer;
import com.example.internship.repository.CustomerRepository;
import com.example.internship.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
@Api( value = "customers", description = "Медоты: получение/редактирование/поиск данных пользователя")

public class CustomerRestController {
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    //Показать всех пользователей
    @GetMapping("")
    @ApiOperation(value = "Показать список пользователей", response = Iterable.class)
    public Iterable<Customer> list(){
        return customerService.getAll();
    }

    //Показать данные конкретного пользователя
    @GetMapping("{id}")
    @ApiOperation(value = "Получение данных пользователя по идентификатору", response = Customer.class)
    public Optional<Customer> getUser(@PathVariable Long id){
        return customerService.getById(id);
    }

    //Редактирование данных
    @PutMapping("{id}")
    @ApiOperation(value = "Редактирование данных")
    public ResponseEntity postUser(@PathVariable Long id, @RequestBody Customer customer){
        Customer customerOld = customerService.getById(id).orElse(null);
        customerOld.setFirstName(customer.getFirstName());
        customerOld.setMiddleName(customer.getMiddleName());
        customerOld.setLastName(customer.getLastName());
        customerOld.setAddresses(customer.getAddresses());
        customerOld.setCart(customer.getCart());
        customerOld.setEmail(customer.getEmail());
        customerOld.setPassword(customer.getPassword());
        customerOld.setPhone(customer.getPhone());
        customerService.save(customer);
        return new ResponseEntity("Данные пользователя обновлены!", HttpStatus.OK);
    }

    //Поиск пользователей
    @GetMapping("search")
    @ApiOperation(value = "Поиск пользователей")
    public List<Customer> searchUser(@RequestParam(name = "firstName", required = false )
                                     @ApiParam(value = "Поиск по имени") String firstName,
                                     @RequestParam(name = "middleName", required = false )
                                     @ApiParam(value = "Поиск по отчеству") String middleName,
                                     @RequestParam(name = "lastName", required = false )
                                     @ApiParam(value = "Поиск по фамилии") String lastName,
                                     @RequestParam(name = "email", required = false)
                                     @ApiParam(value = "Поиск по email") String email){

        return customerRepository.searchUsers(notNullString(firstName),notNullString(middleName),
                                              notNullString(lastName),notNullString(email));
    }

    private String notNullString(String searchString){
        if (searchString == null){
            searchString="";
        }
        return searchString.toLowerCase();
    }
}
