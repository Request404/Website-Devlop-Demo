package com.hey.request.system.exception.state;

public enum StateCode {

  PERMISSION_DENIED(401, "Permission Denied"),
  SYSTEM_FORBIDDEN(403, "Authorization Failed"),
  UN_KNOW_EXCEPTION(1000, "System Unknown Exception"),
  NULL_POINT_EXCEPTION(1001, "Null Point Exception"),
  RESPONSE_JSON_EXCEPTION(1002, "Responding JSON Exception  "),
  DATA_VALID_EXCEPTION(1003, "Abnormal Data Verification"),
  USER_EXIST_EXCEPTION(1010, "The User Already Exists"),
  USER_NOT_FOUND_EXCEPTION(1011, "The User Couldn't Be Found"),
  USER_EMAIL_REPEAT_EXCEPTION(1012, "The User Email Has Been Registered"),
  USER_EMAIL_NOT_FOUND_EXCEPTION(1013, "The User Email Couldn't Be Found"),
  VERIFICATION_CODE_ERROR_EXCEPTION(1015, "The Verification Code Fails");


  private final int code;
  private final String msg;

  StateCode(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }
}