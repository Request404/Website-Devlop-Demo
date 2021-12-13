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
@ApiModel(value="DMovies对象", description="")
public class DMovies implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "电影编号")
    @TableId(value = "id", type = IdType.AUTO)
    @Null(message = "id编号不可指定", groups = {CreateValidGroup.class})
    private Long id;

    @ApiModelProperty(value = "电影名")
    @NotBlank(message = "电影名不能为空", groups = {CreateValidGroup.class})
    private String name;

    @ApiModelProperty(value = "题材类型")
    private String type;

    @ApiModelProperty(value = "制片地区")
    private String country;

    @ApiModelProperty(value = "发行日期")
    private String releaseDate;

    @ApiModelProperty(value = "导演")
    private String director;

    @ApiModelProperty(value = "编剧")
    private String writer;

    @ApiModelProperty(value = "演员表")
    private String actors;

    @ApiModelProperty(value = "别名")
    private String alternateName;

    @ApiModelProperty(value = "时长（分钟）")
    private Integer minutes;

    @ApiModelProperty(value = "电影简介")
    private String introduction;

    @ApiModelProperty(value = "封面地址")
    private String coverUrl;

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
    private Integer removeState;

    @ApiModelProperty(value = "评分数据")
    @TableField(exist = false)
    private UScore score;

}
