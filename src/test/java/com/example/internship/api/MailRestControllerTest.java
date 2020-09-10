package com.example.internship.api;

import com.example.internship.dto.CustomerDto;
import com.example.internship.mail.exception.MailServiceException;
import com.example.internship.mail.service.EmailService;
import com.example.internship.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MailRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private EmailService emailService;

    @Autowired
    private MailRestController mailRestController;

    private final Long CUSTOMER_ID1 = 1L;

    @Before
    public void before() throws MailServiceException {
        CustomerDto customer = new CustomerDto();
        customer.setId(CUSTOMER_ID1);
        customer.setEmail("a@a.com");
        when(customerService.getDtoById(eq(CUSTOMER_ID1))).thenReturn(Optional.of(customer));
        when(emailService.sendRegistrationWelcomeMessage(any(CustomerDto.class))).thenReturn(true);
    }

    @Test
    public void shouldSendEmail() throws Exception {
        this.mockMvc.perform(get("/api/mail/1/welcome")).andDo(print()).andExpect(status().isOk());
    }
}
