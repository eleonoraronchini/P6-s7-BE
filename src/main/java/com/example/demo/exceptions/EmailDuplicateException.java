package com.example.demo.exceptions;

public class EmailDuplicateException extends RuntimeException {
  public EmailDuplicateException(String message) {
    super("Email già esistente");
  }
}
