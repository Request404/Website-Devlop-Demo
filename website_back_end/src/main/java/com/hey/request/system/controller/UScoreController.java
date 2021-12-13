package com.hey.request.system.controller;


import com.hey.request.system.annotation.SystemLog;
import com.hey.request.system.entity.UScore;
import com.hey.request.system.service.UScoreService;
import com.hey.request.system.utils.Q;
import com.hey.request.system.utils.group.UpdateValidGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Request404
 * @since 2021-11-15
 */
@Api(value = "用户评分数据操作API")
@RestController
@RequestMapping("/u-score")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UScoreController {

  private final UScoreService uScoreService;

  @SystemLog
  @PostMapping("/update")
  @ApiOperation(value = "评分信息更新", notes = "需要传入UScore对象， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q updateProductScore(@Validated({UpdateValidGroup.class}) @RequestBody UScore score) {
    Integer update = uScoreService.updateProductScore(score);
    if (update == 0) {
      return Q.error(500, "评分信息更新失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", update)).put("msg", "评分信息更新成功");
    }
  }

  @SystemLog
  @GetMapping("/website/open/visit")
  @ApiOperation(value = "文化产品访问记录", notes = "需要传入productId")
  public Q visitRecord(Long productId) {
    return Q.ok().put("data", uScoreService.visitRecord(productId));
  }

  @SystemLog
  @GetMapping("/website/open/getScoreStatistical")
  @ApiOperation(value = "获取评分统计后的产品排名", notes = "需要传入pageNumber 页码；pageSize 每页个数；productType 产品类型，返回评分统计后的产品排名")
  public Q getScoreStatistical(Integer pageNumber, Integer pageSize, Integer productType) {
    return uScoreService.getScoreStatistical(pageNumber, pageSize, productType);
  }

  @SystemLog
  @GetMapping("/website/open/getOtherStatistical")
  @ApiOperation(value = "获取其他（访问数）统计后的产品排名", notes = "需要传入pageNumber 页码；pageSize 每页个数；productType 产品类型，返回访问数统计后的产品排名")
  public Q getOtherStatistical(Integer pageNumber, Integer pageSize, Integer productType) {
    return uScoreService.getOtherStatistical(pageNumber, pageSize, productType);
  }

  @SystemLog
  @GetMapping("/website/open/getNewProduction")
  @ApiOperation(value = "获取近7天上架产品列表", notes = "需要传入pageNumber 页码；pageSize 每页个数；productType 产品类型，返回近7天上架产品列表")
  public Q getNewProduction(Integer pageNumber, Integer pageSize, Integer productType) {
    return uScoreService.getNewProduction(pageNumber, pageSize, productType);
  }

  @SystemLog
  @GetMapping("/website/open/getTotalStatistical")
  @ApiOperation(value = "获取总分统计后的产品排名", notes = "需要传入pageNumber 页码；pageSize 每页个数；productType 产品类型，返回总分统计后的产品排名")
  public Q getTotalStatistical(Integer pageNumber, Integer pageSize, Integer productType) {
    return uScoreService.getTotalStatistical(pageNumber, pageSize, productType);
  }

  @SystemLog
  @GetMapping("/getAllScoreData")
  @ApiOperation(value = "获取所有评分数据", notes = "需要传入pageNumber 页码；pageSize 每页个数；condition 模糊查询条件，返回获取所有评分数据")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q getAllScoreData(Integer pageNumber, Integer pageSize, Integer condition) {
    return uScoreService.getAllScoreData(pageNumber, pageSize, condition);
  }
}

