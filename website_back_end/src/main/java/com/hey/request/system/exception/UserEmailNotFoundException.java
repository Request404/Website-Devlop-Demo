package com.hey.request.system.exception;

public class UserEmailNotFoundException extends RuntimeException {

  private final static long serialVersionUID = 1L;

  public UserEmailNotFoundException() {
    super();
  }

  public UserEmailNotFoundException(String s) {
    super(s);
  }
}