package com.hey.request.security.handler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hey.request.security.utils.ResponseUtil;
import com.hey.request.system.exception.ResponseJsonException;
import com.hey.request.system.exception.state.StateCode;
import com.hey.request.system.utils.Q;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 无权限处理类
 */
@Slf4j
@Component
public class UserAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
                     AccessDeniedException accessDeniedException) throws IOException, ServletException {
    response.setStatus(401);
    ResponseUtil.response(response, Q.error(StateCode.SYSTEM_FORBIDDEN.getCode(), StateCode.SYSTEM_FORBIDDEN.getMsg()).put("data", accessDeniedException.getMessage()));
  }

}