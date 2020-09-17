package com.example.internship.service.customer;

import com.example.internship.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerCleanerServiceImpl implements CustomerCleanerService {

    private final CustomerRepository customerRepository;

    @Override
    public Integer cleanInactiveAnonymousCustomers() {
        Integer deletedCustomersCount = customerRepository.deleteInactiveAnonymousUsers();
        log.info("Executing cleanInactiveAnonymousCustomers, deleted " + deletedCustomersCount +
                " customers");
        return deletedCustomersCount;
    }
}
