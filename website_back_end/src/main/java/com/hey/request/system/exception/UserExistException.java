package com.hey.request.system.exception;

public class UserExistException extends RuntimeException {

  private final static long serialVersionUID = 1L;


  public UserExistException() {
    super();
  }

  public UserExistException(String s) {
    super(s);
  }
}
