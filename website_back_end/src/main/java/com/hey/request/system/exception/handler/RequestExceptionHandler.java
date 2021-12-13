package com.hey.request.system.exception.handler;

import com.hey.request.system.exception.*;
import com.hey.request.system.exception.state.StateCode;
import com.hey.request.system.utils.Q;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@Slf4j
@RestControllerAdvice(basePackages = "com.hey.request.system.controller")
public class RequestExceptionHandler {

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public Q handleValidException(MethodArgumentNotValidException e) {

    BindingResult result = e.getBindingResult();

    HashMap<String, String> errorMap = new HashMap<>();

    result.getFieldErrors().forEach(fieldError -> {
      errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
    });

    return Q.error(StateCode.DATA_VALID_EXCEPTION.getCode(), StateCode.DATA_VALID_EXCEPTION.getMsg()).put("data", errorMap);
  }

  @ExceptionHandler(value = NullPointerException.class)
  public Q handlerNullPointException() {
    return Q.error(StateCode.NULL_POINT_EXCEPTION.getCode(), StateCode.NULL_POINT_EXCEPTION.getMsg());
  }

  @ExceptionHandler(value = ResponseJsonException.class)
  public Q handlerResponseJsonException() {
    log.error("Response 输出 JSON 格式异常：" + StateCode.RESPONSE_JSON_EXCEPTION.getCode());
    return Q.error(StateCode.RESPONSE_JSON_EXCEPTION.getCode(), StateCode.RESPONSE_JSON_EXCEPTION.getMsg());
  }

  @ExceptionHandler(value = UserExistException.class)
  public Q handlerUserExistException() {
    return Q.error(StateCode.USER_EXIST_EXCEPTION.getCode(), StateCode.USER_EXIST_EXCEPTION.getMsg()).put("data", -1);
  }

  @ExceptionHandler(value = UserNotFoundException.class)
  public Q handlerUserNotFoundException() {
    return Q.error(StateCode.USER_NOT_FOUND_EXCEPTION.getCode(), StateCode.USER_NOT_FOUND_EXCEPTION.getMsg()).put("data", -1);
  }

  @ExceptionHandler(value = UserEmailRepeatException.class)
  public Q handlerUserEmailRepeatException() {
    return Q.error(StateCode.USER_EMAIL_REPEAT_EXCEPTION.getCode(), StateCode.USER_EMAIL_REPEAT_EXCEPTION.getMsg());
  }

  @ExceptionHandler(value = UserEmailNotFoundException.class)
  public Q handlerUserEmailNotFoundException() {
    return Q.error(StateCode.USER_EMAIL_NOT_FOUND_EXCEPTION.getCode(), StateCode.USER_EMAIL_NOT_FOUND_EXCEPTION.getMsg());
  }

  @ExceptionHandler(value = VerificationCodeErrorException.class)
  public Q handlerVerificationCodeErrorException() {
    return Q.error(StateCode.VERIFICATION_CODE_ERROR_EXCEPTION.getCode(), StateCode.VERIFICATION_CODE_ERROR_EXCEPTION.getMsg()).put("data", -2);
  }

}
