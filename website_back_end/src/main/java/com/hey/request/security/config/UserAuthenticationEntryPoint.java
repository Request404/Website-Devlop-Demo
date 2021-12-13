package com.hey.request.security.config;

import com.hey.request.security.utils.ResponseUtil;
import com.hey.request.system.exception.state.StateCode;
import com.hey.request.system.utils.Q;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
                       AuthenticationException authException) throws IOException, ServletException {
    response.setStatus(403);
    ResponseUtil.response(response, Q.error(StateCode.SYSTEM_FORBIDDEN.getCode(), StateCode.SYSTEM_FORBIDDEN.getMsg()).put("data", "用户认证失败,你所访问的资源需要进行认证授权才能进行访问！"));
  }
}
