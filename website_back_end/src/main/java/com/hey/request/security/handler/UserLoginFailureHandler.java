package com.hey.request.security.handler;

import com.hey.request.security.utils.ResponseUtil;
import com.hey.request.system.exception.ResponseJsonException;
import com.hey.request.system.exception.state.StateCode;
import com.hey.request.system.utils.Q;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 登录失败处理类
 */
@Slf4j
@Component
public class UserLoginFailureHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                      AuthenticationException exception) {
    ResponseUtil.response(response, Q.error(500, "登录失败").put("data", exception.getMessage()));
  }
}