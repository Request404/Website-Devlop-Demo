package com.hey.request.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.hey.request.system.utils.group.CreateValidGroup;
import com.hey.request.system.utils.group.UpdateValidGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
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
@ApiModel(value="UReport对象", description="")
public class UReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    @Null(message = "id编号不可指定", groups = {CreateValidGroup.class})
    private Long id;

    @ApiModelProperty(value = "用户编号")
    @NotBlank(message = "用户编号不能为空", groups = {CreateValidGroup.class})
    private Long userId;

    @ApiModelProperty(value = "评论编号")
    @NotBlank(message = "评论编号不能为空", groups = {CreateValidGroup.class})
    private Long commentId;

    @ApiModelProperty(value = "举报类型")
    private String reportType;

    @ApiModelProperty(value = "处理人")
    private String handler;

    @ApiModelProperty(value = "回复信息")
    private String handlerReply;

    @ApiModelProperty(value = "操作时间")
    @Null(message = "操作时间不可指定", groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private String operationTime;

    @ApiModelProperty(value = "操作人")
    @Null(message = "操作人不可指定", groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private String operationUser;

    @ApiModelProperty(value = "状态")
    @Null(message = "该字段为系统占用字段，不可指定", groups = {CreateValidGroup.class})
    private Short state;

    @TableLogic
    @ApiModelProperty(value = "删除状态")
    @Null(message = "删除状态不可指定", groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private Integer removeState;


}
