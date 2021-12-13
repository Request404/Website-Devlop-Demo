package com.hey.request.security.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hey.request.security.entity.SysUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

@Slf4j
public class JwtTokenUtil {

  /**
   * 创建Token
   * @param sysUserDetails 用户信息
   * @return token
   */
  public static String createAccessToken(SysUserDetails sysUserDetails) {
    long now = System.currentTimeMillis();//当前毫秒
    long exp = now + JwtConfig.expiration;//加上配置文件中的过期时间(单位毫秒)

    JwtBuilder jwtBuilder = Jwts.builder()// 设置JWT
        .setId(sysUserDetails.getId().toString()) // 用户Id
        .setSubject(sysUserDetails.getUsername()) // 主题
        .setIssuer("Reuqest404") // 签发者
        .signWith(SignatureAlgorithm.HS512, JwtConfig.secret) // 签名算法、密钥
        .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
        .claim("role",  sysUserDetails.getRole())
        .claim("authentications", sysUserDetails.getAuthentication());

    String token = jwtBuilder.compact();
    return JwtConfig.tokenPrefix + token;
  }

  /**
   * 解析Token
   * @param token Token信息
   * @return SysUserDetails
   */
  public static SysUserDetails parseAccessToken(String token) {
    SysUserDetails sysUserDetails = null;
    if (StringUtils.isNotEmpty(token)) {
      try {
        // 去除JWT前缀
        token = token.substring(JwtConfig.tokenPrefix.length());
        System.out.println("JwtConfig.secret:"+JwtConfig.secret);
        // 解析Token
        Claims claims = Jwts.parser().setSigningKey(JwtConfig.secret).parseClaimsJws(token).getBody();

        // 获取用户信息
        sysUserDetails = new SysUserDetails();
        sysUserDetails.setId(Long.parseLong(claims.getId()));
        sysUserDetails.setUsername(claims.getSubject());
        sysUserDetails.setRole(claims.get("role").toString());
        // 获取角色
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        String authority = claims.get("authentications").toString();

        authorities.add(new SimpleGrantedAuthority(authority));

        sysUserDetails.setAuthorities(authorities);
      } catch (Exception e) {
        log.error("解析Token异常：" + e);
      }
    }
    return sysUserDetails;
  }

}