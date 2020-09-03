package com.example.internship.service.user;

import com.example.internship.entity.Customer;
import com.example.internship.repository.CustomerRepository;
import com.example.internship.security.CustomerPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService{
    @Autowired
    private CustomerRepository customerRepository;

    //Получение пользователя по email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException(email);
        }

//        UserDetails user = User.builder()
//                .username(customer.getEmail())
//                .password(customer.getPassword())
//                .build();

        CustomerPrincipal customerPrincipal = new CustomerPrincipal(customer);
        return customerPrincipal;
    }
}