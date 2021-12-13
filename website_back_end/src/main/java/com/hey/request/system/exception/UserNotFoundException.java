package com.hey.request.system.exception;

public class UserNotFoundException extends RuntimeException {

  private final static long serialVersionUID = 1L;

  public UserNotFoundException() {
    super();
  }

  public UserNotFoundException(String s) {
    super(s);
  }
}
