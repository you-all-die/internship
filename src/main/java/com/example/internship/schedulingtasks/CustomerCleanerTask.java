package com.example.internship.schedulingtasks;

import com.example.internship.service.customer.CustomerCleanerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Murashov A.A
 */
@Component
@RequiredArgsConstructor
public class CustomerCleanerTask {
    private final CustomerCleanerService cleanerService;

    //вызов запланированного задания в запланированное время
    //формат входных данных - (секунда, минута, час, день месяца, месяц, день недели), * - любое
    //задача вызовется каждый день в 2:30:00
    @Scheduled(cron = "0 30 2 * * *")
    public void cleanAnonymousCustomers() {
        cleanerService.cleanInactiveAnonymousCustomers();
    }
}
