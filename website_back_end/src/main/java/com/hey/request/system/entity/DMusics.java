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
@ApiModel(value="DMusics对象", description="")
public class DMusics implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "歌曲编号")
    @TableId(value = "id", type = IdType.AUTO)
    @Null(message = "id编号不可指定", groups = {CreateValidGroup.class})
    private Long id;

    @ApiModelProperty(value = "歌曲名")
    @NotBlank(message = "歌曲名不能为空", groups = {CreateValidGroup.class})
    private String name;

    @ApiModelProperty(value = "专辑")
    private String album;

    @ApiModelProperty(value = "演唱者")
    private String singer;

    @ApiModelProperty(value = "作词人")
    private String writeWords;

    @ApiModelProperty(value = "作曲人")
    private String compose;

    @ApiModelProperty(value = "歌词")
    private String lyric;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "发行地区")
    private String country;

    @ApiModelProperty(value = "长度（秒）")
    private Integer minutes;

    @ApiModelProperty(value = "发行年份")
    private Integer releaseYear;

    @ApiModelProperty(value = "专辑封面地址")
    private String albumAvatarUrl;

    @ApiModelProperty(value = "歌手封面地址")
    private String singerAvatarUrl;

    @ApiModelProperty(value = "操作人")
    @Null(message = "操作人不可指定", groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private String operationTime;

    @ApiModelProperty(value = "操作时间")
    @Null(message = "操作时间不可指定", groups = {CreateValidGroup.class, UpdateValidGroup.class})
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
