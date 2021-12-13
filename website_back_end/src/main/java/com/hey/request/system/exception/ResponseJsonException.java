package com.hey.request.system.exception;

public class ResponseJsonException extends RuntimeException{

  private final static long serialVersionUID = 1L;

  public ResponseJsonException() {
    super();
  }

  public ResponseJsonException(String s) {
    super(s);
  }
}
