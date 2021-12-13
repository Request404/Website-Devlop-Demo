package com.hey.request.security.handler;

import com.hey.request.security.utils.ResponseUtil;
import com.hey.request.system.exception.ResponseJsonException;
import com.hey.request.system.utils.Q;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * 登出成功处理类
 */
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                              Authentication authentication) {
    SecurityContextHolder.clearContext();
    ResponseUtil.response(response, Objects.requireNonNull(Q.ok().put("msg", "登出成功")).put("data", null));
  }
}
