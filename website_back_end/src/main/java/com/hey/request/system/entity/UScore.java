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
@ApiModel(value="UScore对象", description="")
public class UScore implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "产品编号")
    @NotBlank(message = "产品编号不能为空", groups = {CreateValidGroup.class})
    private Long productId;

    @ApiModelProperty(value = "文化产品类型")
    private Short productType;

    @ApiModelProperty(value = "一星评分人数")
    private Integer oneStar;

    @ApiModelProperty(value = "二星评分人数")
    private Integer twoStar;

    @ApiModelProperty(value = "三星评分人数")
    private Integer threeStar;

    @ApiModelProperty(value = "四星评分人数")
    private Integer fourStar;

    @ApiModelProperty(value = "五星评分人数")
    private Integer fiveStar;

    @ApiModelProperty(value = "评分总人数")
    private Long total;

    @ApiModelProperty(value = "产品统计得分")
    private Integer score;

    @ApiModelProperty(value = "七天总访问人数")
    private Integer sevenVisit;

    @ApiModelProperty(value = "七天访问人数")
    private String recordDateVisit;

    @ApiModelProperty(value = "今日访问人数")
    private Integer todayVisit;

    @ApiModelProperty(value = "昨日访问人数")
    private Integer yesterdayVisit;

    @ApiModelProperty(value = "上架日期")
    private String postDate;

    @ApiModelProperty(value = "产品得分")
    private Double statisticPoint;

    @TableLogic
    @ApiModelProperty(value = "删除状态")
    @Null(message = "删除状态不可指定", groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private Integer removeState;


}
