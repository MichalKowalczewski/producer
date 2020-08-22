package com.example.producer.exceptions;

public class UserNotFoundException extends RuntimeException {

  @Override
  public String getMessage() {
    return "Could not find searched User";
  }

}
