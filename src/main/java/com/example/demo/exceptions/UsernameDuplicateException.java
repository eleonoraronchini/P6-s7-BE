package com.example.demo.exceptions;

public class UsernameDuplicateException extends RuntimeException {
    public UsernameDuplicateException(String message) {
        super("Username già esistente");
    }
}
