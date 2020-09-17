package com.example.internship.schedulingtasks;

import com.example.internship.service.customer.CustomerCleanerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerCleanerTask {
    private final CustomerCleanerService cleanerService;

    @Scheduled(cron = "0 30 2 * * *")
    public void cleanAnonymousCustomers() {
        cleanerService.cleanInactiveAnonymousCustomers();
    }
}
