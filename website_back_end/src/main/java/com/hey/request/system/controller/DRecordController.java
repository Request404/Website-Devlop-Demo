package com.hey.request.system.controller;


import com.hey.request.system.annotation.SystemLog;
import com.hey.request.system.entity.DRecord;
import com.hey.request.system.service.DRecordService;
import com.hey.request.system.utils.Q;
import com.hey.request.system.utils.group.CreateValidGroup;
import com.hey.request.system.utils.group.UpdateValidGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Request404
 * @since 2021-11-28
 */
@Api(value = "推荐记录操作API")
@RestController
@RequestMapping("/d-record")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DRecordController {

  private final DRecordService dRecordService;

  @SystemLog
  @PutMapping("/add")
  @ApiOperation(value = "添加推荐记录", notes = "需要传入DRecord对象， 添加成功 1， 添加失败 0，数据重复-1")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q addRecommendRecord(Authentication authentication, @Validated({CreateValidGroup.class}) @RequestBody DRecord dRecord) {
    Integer add = dRecordService.addRecommendRecord(authentication, dRecord);
    if (add == 0) {
      return Q.error(500, "推荐记录添加失败");
    } else if (add == -1) {
      return Q.error(500, "推荐记录数据重复");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", add)).put("msg", "推荐记录添加成功");
    }
  }

  @SystemLog
  @DeleteMapping("/delete")
  @ApiOperation(value = "删除推荐记录", notes = "需要传入dRecordId， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q removeRecommendRecord(Long dRecordId) {
    Integer remove = dRecordService.removeRecommendRecord(dRecordId);
    if (remove == 0) {
      return Q.error(500, "推荐记录删除失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", remove)).put("msg", "推荐记录删除成功");
    }
  }

  @SystemLog
  @PostMapping("/update")
  @ApiOperation(value = "更新推荐记录", notes = "需要传入DRecord对象， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q updateRecommendRecord(Authentication authentication, @Validated({UpdateValidGroup.class}) @RequestBody DRecord dRecord) {
    Integer update = dRecordService.updateRecommendRecord(authentication, dRecord);
    if (update == 0) {
      return Q.error(500, "推荐记录更新失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", update)).put("msg", "推荐记录更新成功");
    }
  }

  @SystemLog
  @DeleteMapping("/getOff")
  @ApiOperation(value = "下架推荐记录", notes = "需要传入dRecordId， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q getOffRecommendRecord(Long dRecordId) {
    Integer update = dRecordService.getOffRecommendRecord(dRecordId);
    if (update == 0) {
      return Q.error(500, "推荐记录更新失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", update)).put("msg", "推荐记录更新成功");
    }
  }

  @SystemLog
  @GetMapping("/website/open/getRecommendRecord")
  @ApiOperation(value = "获取推荐记录数据", notes = "需要传入pageNumber 页码；pageSize 每页个数；condition 模糊查询条件，返回所有推荐记录数据")
  public Q getRecommendRecord(Integer pageNumber, Integer pageSize, Integer condition) {
    return dRecordService.getRecommendRecord(pageNumber, pageSize, condition);
  }

  @SystemLog
  @GetMapping("/website/open/getIndexCubeRecommend")
  @ApiOperation(value = "获取推荐记录数据", notes = "返回4条推荐记录数据")
  public Q getRecommendRecord() {
    return dRecordService.getIndexCubeRecommend();
  }

}

