package com.hey.request.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.sql.Blob;
import java.io.Serializable;

import com.hey.request.system.utils.group.CreateValidGroup;
import com.hey.request.system.utils.group.UpdateValidGroup;
import com.hey.request.system.utils.valid.StringListValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

/**
 * <p>
 *
 * </p>
 *
 * @author request404
 * @since 2021-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID编号")
    @TableId(value = "id", type = IdType.AUTO)
    @Null(message = "id编号不可指定", groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private Long id;

    @ApiModelProperty(value = "用户")
    @NotBlank(message = "用户名不能为空", groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空", groups = {CreateValidGroup.class})
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "性别")
    @StringListValid(message = "性别选择有误", values = {"男", "女", "无性别者", "其他"}, groups = {UpdateValidGroup.class})
    private String gender;

    @ApiModelProperty(value = "手机号")
    @Pattern(regexp = "^1(?:3\\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\\d|9\\d)\\d{8}$",
        message = "手机号码格式不正确",
        groups = {UpdateValidGroup.class})
    private String telephone;

    @ApiModelProperty(value = "邮箱")
    @Email(message = "邮箱格式不正确", groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private String email;

    @ApiModelProperty(value = "头像")
    @URL(message = "头像网络路径格式不正确", groups = {UpdateValidGroup.class})
    private String headPortrait;

    @ApiModelProperty(value = "角色")
    @StringListValid(message = "该角色身份不存在", values = {"user", "admin", "root", "other"}, groups = {UpdateValidGroup.class})
    private String role;

    @ApiModelProperty(value = "权限")
    @Null(message = "权限暂不可用更改", groups = {CreateValidGroup.class, UpdateValidGroup.class,})
    private String authentication;

    @ApiModelProperty(value = "状态码")
    @Null(message = "该字段为系统占用字段，不可指定", groups = {CreateValidGroup.class, UpdateValidGroup.class,})
    private Short state;

    @ApiModelProperty(value = "操作时间")
    @Null(message = "该字段为系统占用字段，不可指定", groups = {CreateValidGroup.class, UpdateValidGroup.class,})
    private String operationTime;

    @ApiModelProperty(value = "操作用户")
    @Null(message = "该字段为系统占用字段，不可指定", groups = {CreateValidGroup.class, UpdateValidGroup.class,})
    private String operationUser;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic(value = "1", delval = "0")
    @Null(message = "该字段为系统占用字段，不可指定", groups = {CreateValidGroup.class, UpdateValidGroup.class,})
    private Short removeState;

    @ApiModelProperty(value = "存放验证码")
    @TableField(exist = false)
    private String verifyCode;
}
