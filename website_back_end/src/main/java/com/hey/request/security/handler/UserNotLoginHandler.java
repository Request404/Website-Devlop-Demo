package com.hey.request.security.handler;

import com.hey.request.security.utils.ResponseUtil;
import com.hey.request.system.exception.ResponseJsonException;
import com.hey.request.system.exception.state.StateCode;
import com.hey.request.system.utils.Q;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 未登录处理类
 */
@Component
public class UserNotLoginHandler implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
                       AuthenticationException authException) throws IOException, ServletException {
    ResponseUtil.response(response, Q.error(StateCode.PERMISSION_DENIED.getCode(), StateCode.PERMISSION_DENIED.getMsg()).put("data", authException.getMessage()));
  }
}