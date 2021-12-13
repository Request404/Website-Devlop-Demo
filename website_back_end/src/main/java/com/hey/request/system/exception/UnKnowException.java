package com.hey.request.system.exception;

public class UnKnowException extends RuntimeException {

  private final static long serialVersionUID = 1L;

  public UnKnowException() {
    super();
  }

  public UnKnowException(String s) {
    super(s);
  }

}