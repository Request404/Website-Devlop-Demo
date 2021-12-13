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
@ApiModel(value="UComplaints对象", description="")
public class UComplaints implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    @Null(message = "id编号不可指定", groups = {CreateValidGroup.class})
    private Long id;

    @ApiModelProperty(value = "用户编号")
    @NotNull(message = "用户编号不能为空", groups = {CreateValidGroup.class})
    private Long userId;

    @ApiModelProperty(value = "投诉内容")
    private String complaintContent;

    @ApiModelProperty(value = "处理人")
    private String handler;

    @ApiModelProperty(value = "处理回复")
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
