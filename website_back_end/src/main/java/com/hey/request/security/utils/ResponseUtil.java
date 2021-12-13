package com.hey.request.security.utils;

import com.alibaba.fastjson.JSONObject;
import com.hey.request.system.exception.ResponseJsonException;
import com.hey.request.system.exception.state.StateCode;
import com.hey.request.system.utils.Q;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ResponseUtil {

  public static void response(HttpServletResponse res, Q q) {
    PrintWriter out = null;
    try {
      res.setCharacterEncoding("UTF-8");
      res.setContentType("application/json");
      JSONObject jsonObj=new JSONObject(q);
      out = res.getWriter();
      out.println(jsonObj);
      out.flush();
    } catch (Exception e) {
      throw new ResponseJsonException();
    } finally {
      if (out != null) {
        out.close();
      }
    }
  }
}
