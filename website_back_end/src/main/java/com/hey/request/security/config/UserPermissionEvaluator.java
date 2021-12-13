package com.hey.request.security.config;

import com.hey.request.security.entity.SysUserDetails;
import com.hey.request.system.entity.User;
import com.hey.request.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class UserPermissionEvaluator implements PermissionEvaluator {


  @Autowired
  private UserService userService;

  /**
   * 判断是否拥有权限
   *
   * @param authentication 用户身份
   * @param targetUrl      目标路径
   * @param permission     路径权限
   * @return 是否拥有权限
   */
  @Override
  public boolean hasPermission(Authentication authentication, Object targetUrl, Object permission) {
    SysUserDetails sysUserDetails = (SysUserDetails) authentication.getPrincipal();

    Set<String> permissions = new HashSet<String>(); // 用户权限

    System.out.println("permissions =========== "+ permissions );

    User user = userService.findUserByUserName(sysUserDetails.getUsername());

    permissions.add(user.getAuthentication());

    // 判断是否拥有权限
    return permissions.contains(permission.toString());
  }

  @Override
  public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
                               Object permission) {
    return false;
  }

}