package com.hey.request.system.exception;

public class VerificationCodeErrorException extends RuntimeException {

  private final static long serialVersionUID = 1L;


  public VerificationCodeErrorException() {
    super();
  }

  public VerificationCodeErrorException(String s) {
    super(s);
  }
}