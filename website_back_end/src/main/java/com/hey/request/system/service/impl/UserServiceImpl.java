package com.hey.request.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hey.request.security.entity.SysUserDetails;
import com.hey.request.system.entity.User;
import com.hey.request.system.mapper.UserMapper;
import com.hey.request.system.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hey.request.system.utils.Q;
import com.hey.request.system.utils.Utils;
import com.hey.request.system.utils.mail.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author request404
 * @since 2021-09-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

  @Autowired
  private MailUtil mailUtil;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public User findUserByUserName(String username) {
    return this.baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username).eq(User::getState, "1"));
  }

  @Override
  @CachePut(value = "verifyCode", key = "#post")
  public String emailCodeSend(String post, String info) {
    try {
      return mailUtil.mailSend(post, info);
    } catch (MessagingException e) {
      e.printStackTrace();
      return null;
    }
  }


  @Override
  public User getUserInfo(Authentication authentication) {
    SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
    return this.baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername, userDetails.getUsername()));
  }

  @Override
  public User clientSearchInfo(Long userId) {
    return this.baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getId, userId));
  }

  @Override
  public Q searchAllClientUser(Integer pageNumber, Integer pageSize, String condition) {
    if (condition.trim().equals("")) {
      if (0 == pageNumber && 0 == pageSize) {
        return Q.ok().put("data", this.baseMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getRole, "user").orderByAsc(User::getId)));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), new QueryWrapper<User>().lambda().eq(User::getRole, "user").orderByAsc(User::getId)));
      }
    } else {
      QueryWrapper<User> conditionQueryWrapper = new QueryWrapper<User>()
          .eq("role", "user")
          .and(qw -> qw.like("username", condition).or()
              .like("gender", condition).or()
              .like("telephone", condition).or()
              .like("email", condition).or()
              .like("operation_user", condition))
          .orderByAsc("id");
      if (0 == pageNumber && 0 == pageSize) {
        return Q.ok().put("data", this.baseMapper.selectList(conditionQueryWrapper));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), conditionQueryWrapper));
      }
    }
  }

  @Override
  public Q searchAllAdminUser(Integer pageNumber, Integer pageSize, String condition) {
    if (condition.trim().equals("")) {
      if (0 == pageNumber && 0 == pageSize) {
        return Q.ok().put("data", this.baseMapper.selectList(new QueryWrapper<User>().lambda().ne(User::getRole, "user").orderByAsc(User::getId)));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), new QueryWrapper<User>().lambda().ne(User::getRole, "user").orderByAsc(User::getId)));
      }
    } else {
      QueryWrapper<User> conditionQueryWrapper = new QueryWrapper<User>()
          .ne("role", "user")
          .and(qw -> qw.like("username", condition).or()
              .like("gender", condition).or()
              .like("telephone", condition).or()
              .like("email", condition).or()
              .like("operation_user", condition))
          .orderByAsc("id");
      if (0 == pageNumber && 0 == pageSize) {
        return Q.ok().put("data", this.baseMapper.selectList(conditionQueryWrapper));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), conditionQueryWrapper));
      }
    }
  }

  @Override
  public Integer clientUserRegister(User user) {
    if (!this.findEmailRegisterState(user.getEmail())) {
      if (this.verificationCode(user.getEmail(), user.getVerifyCode())) {
        if (!this.findUsernameIsExist(user.getUsername())) {
          user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
          user.setNickName(user.getUsername());
          user.setRole("user");
          user.setAuthentication("user");
          user.setOperationTime(Utils.GenerateDatetime());
          user.setOperationUser("Client System");
          return this.baseMapper.insert(user);
        } else {
          return -1;
        }
      } else {
        return -2;
      }
    } else {
      return -3;
    }
  }

  @Override
  public Integer adminUserRegister(Authentication authentication, User user) {
    SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
    if (!this.findEmailRegisterState(user.getEmail())) {
      if (!this.findUsernameIsExist(user.getUsername())) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setAuthentication(user.getRole());
        user.setOperationTime(Utils.GenerateDatetime());
        user.setOperationUser(userDetails.getUsername());
        return this.baseMapper.insert(user);
      } else {
        return -1;
      }
    } else {
      return -3;
    }
  }

  @Override
  public Integer clientUserInfoUpdate(Authentication authentication, User user) {
    SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
    user.setOperationTime(Utils.GenerateDatetime());
    user.setOperationUser(userDetails.getUsername());
    if (user.getPassword() != null) {
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    }
    if (Utils.authenticationLevel(userDetails.getRole()) < 5) {
      return this.baseMapper.update(user, new UpdateWrapper<User>().lambda().eq(User::getUsername, user.getUsername()));
    } else {
      Short state = this.UserIsOnServer(userDetails.getUsername());
      if (state != 0) {
        user.setPassword(null);
        user.setEmail(null);
        user.setRole(null);
        user.setAuthentication(null);
        if (Objects.equals(userDetails.getUsername(), user.getUsername())) {
          return this.baseMapper.update(user, new UpdateWrapper<User>().lambda().eq(User::getUsername, user.getUsername()));
        } else {
          return -1;
        }
      } else {
        return -2;
      }
    }
  }

  @Override
  public Integer adminUserInfoUpdate(Authentication authentication, User user) {
    SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
    user.setOperationTime(Utils.GenerateDatetime());
    user.setOperationUser(userDetails.getUsername());
    if (user.getPassword() != null) {
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    }
    if (Utils.authenticationLevel(userDetails.getRole()) == 0) {
      if (Objects.equals(user.getUsername(), userDetails.getUsername())) {
        user.setPassword(null);
        user.setEmail(null);
        user.setRole(null);
        user.setAuthentication(null);
      }
      return this.baseMapper.update(user, new UpdateWrapper<User>().lambda().eq(User::getUsername, user.getUsername()));
    } else {
      Short state = this.UserIsOnServer(userDetails.getUsername());
      if (state != 0) {
        if (Objects.equals(userDetails.getUsername(), user.getUsername())) {
          user.setPassword(null);
          user.setEmail(null);
          user.setRole(null);
          user.setAuthentication(null);
          return this.baseMapper.update(user, new UpdateWrapper<User>().lambda().eq(User::getUsername, user.getUsername()));
        } else {
          return -1;
        }
      } else {
        return -2;
      }
    }
  }

  @Override
  public Integer forbiddenUser(Authentication authentication, Long userId) {
    SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
    User user = this.baseMapper.selectById(userId);

    if (Utils.authenticationLevel(userDetails.getRole()) == 0) {
      if (Objects.equals(user.getRole(), "root")) {
        return -2;
      } else {
        if (user.getState() == 1) {
          user.setState((short) 0);
        } else {
          user.setState((short) 1);
        }
        return this.baseMapper.updateById(user);
      }
    } else {
      if (Utils.authenticationLevel(userDetails.getRole()) < 5) {
        if (user.getState() == 1) {
          user.setState((short) 0);
        } else {
          user.setState((short) 1);
        }
        return this.baseMapper.updateById(user);
      } else {
        return -1;
      }
    }
  }

  @Override
  public Integer removeUser(Long userId) {
    User user = this.baseMapper.selectById(userId);
    if (user.getRole().equals("root")) {
      return -1;
    } else {
      return this.baseMapper.deleteById(userId);
    }
  }

  @Override
  public Boolean findUsernameIsExist(String username) {
    return this.baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username)) != null;
  }

  @Override
  public Boolean findEmailRegisterState(String email) {
    return this.baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getEmail, email)) != null;
  }

  @Override
  public Short UserIsOnServer(String content) {
    if (Utils.isMail(content)) {
      User EmailUser = this.baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getEmail, content));
      if (EmailUser != null) {
        return EmailUser.getState();
      } else {
        return 0;
      }
    } else {
      User UsernameUser = this.baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername, content));
      if (UsernameUser != null) {
        return UsernameUser.getState();
      } else {
        return 0;
      }
    }
  }


  public Integer userPasswordAlter(String email, String password, String verifyCode) {
    if (this.UserIsOnServer(email) == 1) {
      if (this.verificationCode(email, verifyCode)) {
        User user = new User();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setOperationTime(Utils.GenerateDatetime());
        user.setOperationUser("Client System");
        return this.baseMapper.update(user, new UpdateWrapper<User>().lambda().eq(User::getEmail, email));
      } else {
        return -1;
      }
    } else {
      return -2;
    }
  }

  public String getUserEmail(String username) {
    User user = this.baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username));
    if (user != null) {
      return user.getEmail();
    } else {
      return null;
    }
  }


  public String getUsername(String email) {
    User user = this.baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getEmail, email));
    if (user != null) {
      return user.getUsername();
    } else {
      return null;
    }
  }

  /**
   * 邮箱验证码校验
   *
   * @param post 邮箱地址
   * @param code 邮箱验证码
   * @return Boolean
   */
  private Boolean verificationCode(String post, String code) {
    String cacheCode = redisTemplate.opsForValue().get("verifyCode::" + post);
    return cacheCode != null && cacheCode.equals("\"" + code + "\"");
  }

}
