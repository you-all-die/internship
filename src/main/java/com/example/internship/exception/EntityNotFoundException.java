package com.example.internship.exception;

/**
 * @author Modenov D.A
 */

public class EntityNotFoundException extends RuntimeException {

    private String messageCode;

    private EntityNotFoundException(String message, String messageCode) {
        super(message);
        this.messageCode = messageCode;
    }

    public static EntityNotFoundException customerNotFound() {
        return new EntityNotFoundException("Customer not found", "exception.customer.not.found.header");
    }

    public static EntityNotFoundException orderNotFound() {
        return new EntityNotFoundException("Order not found", "exception.order.not.found.header");
    }

    public String getMessageCode() {
        return messageCode;
    }
}