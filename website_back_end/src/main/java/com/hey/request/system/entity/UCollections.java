package com.hey.request.system.entity;

import java.util.Date;
import java.io.Serializable;

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
 * @since 2021-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UCollections对象", description="")
public class UCollections implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户编号")
    private Long userId;

    @ApiModelProperty(value = "文化产品编号")
    private Long productId;

    @ApiModelProperty(value = "文化产品类型")
    private Short productType;

    @ApiModelProperty(value = "文化产品名")
    private String productName;

    @ApiModelProperty(value = "文化产品封面地址")
    private String productCoverUrl;

    @ApiModelProperty(value = "收藏时间")
    private String collectTime;

    @ApiModelProperty(value = "状态")
    @Null(message = "状态不可指定", groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private Short state;

}
