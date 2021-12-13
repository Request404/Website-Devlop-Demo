package com.hey.request.security.handler;

import com.hey.request.security.entity.SysUserDetails;
import com.hey.request.security.utils.JwtTokenUtil;
import com.hey.request.security.utils.ResponseUtil;
import com.hey.request.system.exception.ResponseJsonException;
import com.hey.request.system.utils.Q;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 登录成功处理类
 */
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) {
    SysUserDetails sysUserDetails = (SysUserDetails) authentication.getPrincipal();

    String token = JwtTokenUtil.createAccessToken(sysUserDetails);
    ResponseUtil.response(response, Objects.requireNonNull(Q.ok().put("msg", "登录成功")).put("token", token));
  }
}