package com.hey.request.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.hey.request.system.utils.group.CreateValidGroup;
import com.hey.request.system.utils.group.UpdateValidGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Null;

/**
 * <p>
 * 
 * </p>
 *
 * @author Request404
 * @since 2021-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="DRecord对象", description="")
public class DRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    @Null(message = "id编号不可指定", groups = {CreateValidGroup.class})
    private Long id;

    @ApiModelProperty(value = "文化产品编号")
    private Long productId;

    @ApiModelProperty(value = "文化产品类型")
    private Short productType;

    @ApiModelProperty(value = "宣传封面地址")
    private String productCoverUrl;

    @ApiModelProperty(value = "操作时间")
    @Null(message = "操作时间不可指定", groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private String operationTime;

    @ApiModelProperty(value = "操作人")
    @Null(message = "操作人不可指定", groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private String operationUser;

    @ApiModelProperty(value = "状态")
    @Null(message = "状态不可指定", groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private Short state;

    @TableLogic
    @ApiModelProperty(value = "删除状态")
    @Null(message = "删除状态不可指定", groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private Short removeState;

    @ApiModelProperty(value = "产品数据")
    @TableField(exist = false)
    private Object productData;

    @ApiModelProperty(value = "评分数据")
    @TableField(exist = false)
    private UScore score;

}
