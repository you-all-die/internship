package com.example.internship.mail.exception;

public class MailServiceException extends Exception {
    public MailServiceException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
