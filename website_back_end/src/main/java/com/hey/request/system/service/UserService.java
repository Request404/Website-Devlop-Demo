package com.hey.request.system.service;

import com.hey.request.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hey.request.system.utils.Q;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author request404
 * @since 2021-09-07
 */
public interface UserService extends IService<User> {
  /**
   * 根据用户名称查询用户信息
   *
   * @param username 用户名称
   * @return User class
   */
  User findUserByUserName(String username);

  /**
   * 邮箱验证码寄送
   *
   * @param post 邮箱地址
   * @param info 附加信息
   * @return verify code 验证码
   */
  String emailCodeSend(String post, String info);


  /**
   * 获取登录用户信息
   *
   * @param authentication Jwt令牌信息
   * @return 用户信息
   */
  User getUserInfo(Authentication authentication);

  /**
   * 客户端查询用户信息
   *
   * @param userId 用户Id
   * @return 用户
   */
  User clientSearchInfo(Long userId);

  /**
   * 查询客户端用户
   *
   * @param pageNumber 分页的页数
   * @param pageSize   每页个数
   * @param condition  是否带条件
   * @return Client User List In Q.data
   */
  Q searchAllClientUser(Integer pageNumber, Integer pageSize, String condition);

  /**
   * 查询所有管理端用户信息
   *
   * @param pageNumber 分页的页数
   * @param pageSize   每页个数
   * @param condition  是否带条件
   * @return Admin User List In Q.data
   */
  Q searchAllAdminUser(Integer pageNumber, Integer pageSize, String condition);


  /**
   * 客户端用户注册
   *
   * @param user 用户信息
   * @return int
   */
  Integer clientUserRegister(User user);

  /**
   * 管理员端用户注册
   *
   * @param authentication token信息
   * @param user           用户信息
   * @return int
   */
  Integer adminUserRegister(Authentication authentication, User user);

  /**
   * 客户端用户信息修改
   *
   * @param user 用户信息
   * @return int
   */
  Integer clientUserInfoUpdate(Authentication Authentication, User user);

  /**
   * 管理端用户信息修改
   *
   * @param user 用户信息
   * @return int
   */
  Integer adminUserInfoUpdate(Authentication authentication, User user);

  /**
   * 用户密码修改
   *
   * @param email      邮箱
   * @param password   密码
   * @param verifyCode 验证码
   * @return int
   */
  Integer userPasswordAlter(String email, String password, String verifyCode);

  /**
   * 获取用户邮箱
   *
   * @param username 用户名
   * @return String
   */
  String getUserEmail(String username);

  /**
   * 获取用户名
   * @param email 用户邮箱
   * @return String
   */
  String getUsername(String email);

  /**
   * 禁用用户账号
   *
   * @param userId 用户id
   * @return int
   */
  Integer forbiddenUser(Authentication authentication, Long userId);

  /**
   * 删除用户账号
   *
   * @param userId 用户id
   * @return int
   */
  Integer removeUser(Long userId);

  /**
   * 查询用户名是否在数据库中存在
   *
   * @param username 用户名
   * @return 存在 true；不存在 false
   */
  Boolean findUsernameIsExist(String username);

  /**
   * 查询邮箱是否已被注册
   *
   * @param email 邮箱地址
   * @return 存在 true；不存在 false
   */
  Boolean findEmailRegisterState(String email);
  

  /**
   * 查询用户是否禁用
   *
   * @param content 用户名 or 邮箱
   * @return StateCode 0 or 1 or -1
   */
  Short UserIsOnServer(String content);

}
