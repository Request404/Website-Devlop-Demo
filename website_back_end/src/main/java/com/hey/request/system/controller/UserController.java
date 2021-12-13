package com.hey.request.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hey.request.system.annotation.SystemLog;
import com.hey.request.system.entity.User;
import com.hey.request.system.exception.UserEmailRepeatException;
import com.hey.request.system.exception.UserExistException;
import com.hey.request.system.exception.UserNotFoundException;
import com.hey.request.system.exception.VerificationCodeErrorException;
import com.hey.request.system.exception.state.StateCode;
import com.hey.request.system.service.UserService;
import com.hey.request.system.utils.Q;
import com.hey.request.system.utils.Utils;
import com.hey.request.system.utils.group.CreateValidGroup;
import com.hey.request.system.utils.group.UpdateValidGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author request404
 * @since 2021-09-07
 */
@Api(value = "客户端与管理端用户API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

  private final UserService userService;

  @SystemLog
  @GetMapping("/account/email/code")
  @ApiOperation(value = "用户邮箱验证码发送", notes = "需要传入一个正确的邮箱地址，成功 1 失败 0 邮箱格式不正确 -1")
  public Q emailCodeSend(String post, String info) {
    if (Utils.isMail(post)) {
      if (userService.getOne(new QueryWrapper<User>().lambda().eq(User::getEmail, post)) == null) {
        if (userService.emailCodeSend(post, info) != null) {
          return Objects.requireNonNull(Q.ok().put("msg", "邮箱验证发送成功")).put("data", 1);
        } else {
          return Q.error(500, "邮件发送失败").put("data", 0);
        }
      } else {
        return Q.error(500, "该邮箱已注册").put("data", -2);
      }
    } else {
      return Q.error(500, "邮箱格式不正确").put("data", -1);
    }
  }

  @SystemLog
  @GetMapping("/info")
  @ApiOperation(value = "获取用户信息", notes = "需要传入token")
  @PreAuthorize("hasAnyAuthority('user,admin,root')")
  public Q getUserInfo(Authentication authentication) {
    return Q.ok().put("data", userService.getUserInfo(authentication));
  }

  @SystemLog
  @GetMapping("/account/searchAllClientUser")
  @ApiOperation(value = "查询客户端用户", notes = "若需要分页查询则：pageNumber（页码），pageSize（每页个数），condition（带条件查询）")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q searchAllClientUser(Integer pageNumber, Integer pageSize, String condition) {
    return userService.searchAllClientUser(pageNumber, pageSize, condition);
  }

  @SystemLog
  @GetMapping("/account/searchAllAdminUser")
  @ApiOperation(value = "查询管理员用户", notes = "若需要分页查询则：pageNumber（页码），pageSize（每页个数），condition（带条件查询）")
  @PreAuthorize("hasAnyAuthority('root')")
  public Q searchAllAdminUser(Integer pageNumber, Integer pageSize, String condition) {
    return userService.searchAllAdminUser(pageNumber, pageSize, condition);
  }


  @SystemLog
  @PutMapping("/account/register")
  @ApiOperation(value = "客户端用户注册", notes = "需要传入user对象， 添加成功 1， 添加失败 0， 用户名重复 -1， 用户存在 -2，邮箱已被注册 -3")
  public Q clientUserRegister(@Validated({CreateValidGroup.class}) @RequestBody User user) {
    if (Utils.isMail(user.getUsername())) {
      return Q.error(500, "邮箱不能做用户名");
    } else {
      Integer register = userService.clientUserRegister(user);
      if (register == 1) {
        return Objects.requireNonNull(Q.ok().put("msg", "用户注册成功")).put("data", register);
      } else if (register == -1) {
        throw new UserExistException();
      } else if (register == -2) {
        throw new VerificationCodeErrorException();
      } else if (register == -3) {
        throw new UserEmailRepeatException();
      } else {
        return Q.error(500, "用户注册失败");
      }
    }
  }

  @SystemLog
  @PutMapping("/account/addAdministration")
  @ApiOperation(value = "添加管理员", notes = "需要传入user对象， 添加成功 1， 添加失败 0， 用户名重复 -1， 用户存在 -2，邮箱已被注册 -3")
  @PreAuthorize("hasAnyAuthority('root')")
  public Q adminUserRegister(Authentication authentication, @Validated({CreateValidGroup.class}) @RequestBody User user) {
    if (Utils.isMail(user.getUsername())) {
      return Q.error(500, "邮箱不能做用户名");
    } else {
      Integer register = userService.adminUserRegister(authentication, user);
      if (register == 1) {
        return Objects.requireNonNull(Q.ok().put("msg", "用户注册成功")).put("data", register);
      } else if (register == -1) {
        throw new UserExistException();
      } else if (register == -3) {
        throw new UserEmailRepeatException();
      } else {
        return Q.error(500, "管理员添加失败");
      }
    }
  }

  @SystemLog
  @PostMapping("/account/clientUserInfoUpdate")
  @ApiOperation(value = "修改用户信息", notes = "需要传入user对象， 修改成功 1， 修改失败 0， 权限不足 -1， 用户被禁用 -2")
  @PreAuthorize("hasAnyAuthority('user,admin,root')")
  public Q clientUserInfoUpdate(Authentication authentication, @Validated({UpdateValidGroup.class}) @RequestBody User user) {
    Integer update = userService.clientUserInfoUpdate(authentication, user);
    if (update == 1) {
      return Objects.requireNonNull(Q.ok().put("msg", "用户修改成功")).put("data", update);
    } else if (update == -1) {
      return Q.error(StateCode.PERMISSION_DENIED.getCode(), StateCode.PERMISSION_DENIED.getMsg());
    } else if (update == -2) {
      throw new UserNotFoundException();
    } else {
      return Q.error(500, "用户修改失败");
    }
  }

  @SystemLog
  @PostMapping("/account/adminUserInfoUpdate")
  @ApiOperation(value = "修改管理员信息", notes = "需要传入user对象， 修改成功 1， 修改失败 0， 权限不足 -1， 用户被禁用 -2")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q adminUserInfoUpdate(Authentication authentication, @Validated({UpdateValidGroup.class}) @RequestBody User user) {
    Integer update = userService.adminUserInfoUpdate(authentication, user);
    if (update == 1) {
      return Objects.requireNonNull(Q.ok().put("msg", "管理员用户修改成功")).put("data", update);
    } else if (update == -1) {
      return Q.error(StateCode.PERMISSION_DENIED.getCode(), StateCode.PERMISSION_DENIED.getMsg());
    } else if (update == -2) {
      throw new UserNotFoundException();
    } else {
      return Q.error(500, "管理员用户修改失败");
    }
  }

  @SystemLog
  @GetMapping("/account/user/VerifyCode")
  @ApiOperation(value = "用户邮箱验证码发送", notes = "需要传入一个用户名或邮箱地址，成功 1 失败 0 用户不存在 -1")
  public Q sendUserVerifyCode(String user, String info) {
    if (userService.UserIsOnServer(user) == 1) {
      if (Utils.isMail(user)) {
        if (userService.emailCodeSend(user, info) != null) {
          return Objects.requireNonNull(Q.ok().put("msg", "邮箱验证发送成功")).put("data", user);
        } else {
          return Q.error(500, "邮件发送失败").put("data", 0);
        }
      } else {
        String userEmail = userService.getUserEmail(user);
        if (userService.emailCodeSend(userEmail, info) != null) {
          return Objects.requireNonNull(Q.ok().put("msg", "邮箱验证发送成功")).put("data", userEmail);
        } else {
          return Q.error(500, "邮件发送失败").put("data", 0);
        }
      }
    } else {
      throw new UserNotFoundException();
    }
  }

  @SystemLog
  @PostMapping("/account/userPasswordAlter")
  @ApiOperation(value = "用户密码修改", notes = "需要传入user对象， 添加成功 1， 添加失败 0， 用户名不存在 -1， 验证码错误 -2")
  public Q userPasswordAlter(@RequestBody User user) {
    Integer alter = userService.userPasswordAlter(user.getEmail(), user.getPassword(), user.getVerifyCode());
    if (alter == 1) {
      return Objects.requireNonNull(Q.ok().put("msg", "密码修改成功")).put("data", alter);
    } else if (alter == -1) {
      throw new UserNotFoundException();
    } else if (alter == -2) {
      throw new VerificationCodeErrorException();
    } else {
      return Q.error(500, "密码修改失败").put("data", alter);
    }
  }

  @SystemLog
  @DeleteMapping("account/remove")
  @ApiOperation(value = "用户移除", notes = "需要传入userId， 添加成功 1， 添加失败 0")
  @PreAuthorize("hasAnyAuthority('root')")
  public Q userDelete(Long userId) {
    Integer remove = userService.removeUser(userId);
    if (remove == 1) {
      return Objects.requireNonNull(Q.ok().put("msg", "用户删除成功")).put("data", remove);
    } else if (remove == -1) {
      return Q.error(401, "root用户无法删除");
    } else {
      return Q.error(500, "用户删除失败").put("data", remove);
    }
  }

  @SystemLog
  @DeleteMapping("account/forbidden")
  @ApiOperation(value = "用户状态改变", notes = "需要传入userId， 添加成功 1， 添加失败 0")
  @PreAuthorize("hasAnyAuthority('root,admin')")
  public Q forbiddenUser(Authentication authentication, Long userId) {
    Integer forbidden = userService.forbiddenUser(authentication, userId);
    if (forbidden == 1) {
      return Objects.requireNonNull(Q.ok().put("msg", "用户状态改变成功")).put("data", forbidden);
    } else if (forbidden == -1) {
      return Q.error(401, "用户权限不足").put("data", forbidden);
    } else if (forbidden == -2) {
      return Q.error(500, "不能改变自己的状态").put("data", forbidden);
    } else {
      return Q.error(500, "用户状态改变失败").put("data", forbidden);
    }
  }
}

