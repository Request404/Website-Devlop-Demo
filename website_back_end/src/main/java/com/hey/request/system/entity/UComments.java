package com.hey.request.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.hey.request.system.utils.group.CreateValidGroup;
import com.hey.request.system.utils.group.UpdateValidGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * <p>
 * 
 * </p>
 *
 * @author Request404
 * @since 2021-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UComments对象", description="")
public class UComments implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "文化产品编号")
    @NotNull(message = "文化产品编号不能为空", groups = {CreateValidGroup.class})
    private Long productId;


    @ApiModelProperty(value = "文化产品类型")
    private Short productType;

    @ApiModelProperty(value = "用户编号")
    @NotNull(message = "用户编号不能为空", groups = {CreateValidGroup.class})
    private Long userId;

    @ApiModelProperty(value = "用户昵称")
    private String userNickname;

    @ApiModelProperty(value = "用户头像")
    private String avatarUrl;

    @ApiModelProperty(value = "评分")
    private Integer score;

    @ApiModelProperty(value = "评论")
    private String comment;

    @ApiModelProperty(value = "评论时间")
    private String commentTime;

    @ApiModelProperty(value = "状态")
    @Null(message = "状态不可指定", groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private Short state;

    @TableLogic
    @ApiModelProperty(value = "删除状态")
    @Null(message = "删除状态不可指定", groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private Integer removeState;


}
