package com.hey.request.security.config;


import com.hey.request.security.filter.JwtAuthenticationFilter;
import com.hey.request.security.handler.*;
import com.hey.request.security.utils.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

/**
 * 系统安全核心配置
 *
 * @author CL
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启方法权限注解
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  /**
   * 未认证异常处理
   */
  private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;

  /**
   * 无权限处理类
   */
  private final UserAccessDeniedHandler userAccessDeniedHandler;

  /**
   * 用户未登录处理类
   */
  private final UserNotLoginHandler userNotLoginHandler;

  /**
   * 用户登录成功处理类
   */
  private final UserLoginSuccessHandler userLoginSuccessHandler;

  /**
   * 用户登录失败处理类
   */
  private final UserLoginFailureHandler userLoginFailureHandler;

  /**
   * 用户登出成功处理类
   */
  private final UserLogoutSuccessHandler userLogoutSuccessHandler;

  /**
   * 用户登录验证
   */
  private final UserAuthenticationProvider userAuthenticationProvider;

  /**
   * 用户权限注解
   */
  private final UserPermissionEvaluator userPermissionEvaluator;

  /**
   * 加密方式
   */
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }


  /**
   * 注入自定义PermissionEvaluator
   */
  @Bean
  public DefaultWebSecurityExpressionHandler userSecurityExpressionHandler() {
    DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
    handler.setPermissionEvaluator(userPermissionEvaluator);
    return handler;
  }

  /**
   * 用户登录验证
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(userAuthenticationProvider);
  }

  /**
   * 排除swagger拦截
   */
  public static final String[] SWAGGER_WHITELIST = {
      "/swagger-ui.html",
      "/swagger-ui/*",
      "/swagger-resources/**",
      "/v2/api-docs",
      "/v3/api-docs",
      "/webjars/**"
  };

  /**
   * 排除网站公共API的拦截
   */
  public static final String[] WEBSITE_OPEN_API = {
      "/d-books/website/open/*",
      "/d-movies/website/open/*",
      "/d-musics/website/open/*",
      "/d-record/website/open/*",
      "/u-comments/website/open/*",
      "/u-score/website/open/*",
      "/druid/*",
      "/druid/css/*",
      "/druid/js/*",
      "/豆瓣电影爬虫.html",
  };

  /**
   * 安全权限配置
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests() // 权限配置
        .antMatchers(WEBSITE_OPEN_API).permitAll()
        .antMatchers(SWAGGER_WHITELIST).permitAll()
        .antMatchers(JwtConfig.antMatchers.split(",")).permitAll()// 获取白名单（不进行权限验证）
        .anyRequest().authenticated() // 其他的需要登陆后才能访问
        .and().exceptionHandling()
        .authenticationEntryPoint(userAuthenticationEntryPoint)
        .and().httpBasic().authenticationEntryPoint(userNotLoginHandler) // 配置未登录处理类
        .and().formLogin().loginProcessingUrl("/user/account/login")// 配置登录URL
        .successHandler(userLoginSuccessHandler) // 配置登录成功处理类
        .failureHandler(userLoginFailureHandler) // 配置登录失败处理类
        .and().logout().logoutUrl("/user/account/logout")// 配置登出地址
        .logoutSuccessHandler(userLogoutSuccessHandler) // 配置用户登出处理类
        .and().exceptionHandling().accessDeniedHandler(userAccessDeniedHandler)// 配置没有权限处理类
        .and().cors()// 开启跨域
        .and().csrf().disable(); // 禁用跨站请求伪造防护
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 禁用session（使用Token认证）
    http.headers().cacheControl(); // 禁用缓存
    http.addFilter(new JwtAuthenticationFilter(authenticationManager())); //// 添加JWT过滤器
  }
}
