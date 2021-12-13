package com.hey.request.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;

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
@ApiModel(value="DBooks对象", description="")
public class DBooks implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    @Null(message = "id编号不可指定", groups = {CreateValidGroup.class})
    private Long id;

    @ApiModelProperty(value = "书名")
    @NotBlank(message = "书名不能为空", groups = {CreateValidGroup.class})
    private String name;

    @ApiModelProperty(value = "子标题")
    private String title;

    @ApiModelProperty(value = "标签")
    @NotBlank(message = "标签不能为空", groups = {CreateValidGroup.class})
    private String tages;

    @ApiModelProperty(value = "作者")
    @NotBlank(message = "作者不能为空", groups = {CreateValidGroup.class})
    private String author;

    @ApiModelProperty(value = "出版地区")
    private String country;

    @ApiModelProperty(value = "出版日期")
    private String releaseDate;

    @ApiModelProperty(value = "介绍")
    private String introduction;

    @ApiModelProperty(value = "封面地址")
    private String coverUrl;

    @ApiModelProperty(value = "出版社")
    private String publisher;

    @ApiModelProperty(value = "ISBN号")
    private String isbn;

    @ApiModelProperty(value = "参考价格")
    private Double price;

    @ApiModelProperty(value = "价格来源")
    private String priceSource;

    @ApiModelProperty(value = "页数")
    private Integer pages;

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
