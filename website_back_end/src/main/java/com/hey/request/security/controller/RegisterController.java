//package com.hey.request.security.controller;
//
//import com.hey.request.system.entity.User;
//import com.hey.request.system.service.UserService;
//import com.hey.request.system.utils.Q;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 注册用户实例Controller
// */
//@RestController
//@RequestMapping(value = "/register")
//public class RegisterController {
//
//  @Autowired
//  private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//  @Autowired
//  private UserService sysUserService;
//
//  /**
//   * 注册普通用户
//   *
//   * @param username 用户名
//   * @param password 密码
//   */
//  @RequestMapping(value = "/user")
//  public Q user(String username, String password) {
//    User user = new User();
//    user.setUsername(username);
//    user.setPassword(bCryptPasswordEncoder.encode(password));
//    user.setState((short) 1);
//    user.setRole("USER");
//    sysUserService.save(user);
//
//    return Q.ok().put("data", user).put("message", "注册成功");
//  }
//}