package com.hey.request.system.exception;

public class UserEmailRepeatException extends RuntimeException {

  private final static long serialVersionUID = 1L;

  public UserEmailRepeatException() {
    super();
  }

  public UserEmailRepeatException(String s) {
    super(s);
  }
}