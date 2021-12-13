package com.hey.request.security.service;

import com.hey.request.security.entity.SysUserDetails;
import com.hey.request.system.entity.User;
import com.hey.request.system.service.UserService;
import com.hey.request.system.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户登录Service
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserDetailsService implements UserDetailsService {

  private final UserService userService;

  /**
   * 根据用户名查用户信息
   *
   * @param username 用户名
   * @return 用户详细信息
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if(Utils.isMail(username)){
      username = userService.getUsername(username);
    }
    Short state = userService.UserIsOnServer(username);
    if (state == 1) {
      User user = userService.findUserByUserName(username);
      if (user != null) {
        SysUserDetails sysUserDetails = new SysUserDetails();
        BeanUtils.copyProperties(user, sysUserDetails);

        Set<GrantedAuthority> authorities = new HashSet<>(); // 角色集合

        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        sysUserDetails.setAuthorities(authorities);
        return sysUserDetails;
      } else {
        return null;
      }
    } else {
      return null;
    }
  }
}