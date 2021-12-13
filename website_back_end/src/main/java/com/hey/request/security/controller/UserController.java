//package com.hey.request.security.controller;
//
//import com.hey.request.security.entity.SysUserDetails;
//import com.hey.request.system.entity.User;
//import com.hey.request.system.service.UserService;
//import com.hey.request.system.utils.Q;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 普通用户Controller
// */
//@RestController
//@RequestMapping(value = "/user")
//public class UserController {
//
//  @Autowired
//  private UserService userSerivce;
//
//  /**
//   * 查询用户信息
//   *
//   * @return
//   */
//  @PreAuthorize(value = "hasRole('USER')")
//  @RequestMapping(value = "/info")
//  public Q info() {
//    SysUserDetails sysUserDetails = (SysUserDetails) SecurityContextHolder.getContext().getAuthentication()
//        .getPrincipal();
//    User user = userSerivce.getById(sysUserDetails.getId());
//    return Q.ok().put("data", user);
//  }
//}