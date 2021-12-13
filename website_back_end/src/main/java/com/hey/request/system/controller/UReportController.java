package com.hey.request.system.controller;


import com.hey.request.system.annotation.SystemLog;
import com.hey.request.system.entity.UReport;
import com.hey.request.system.service.UReportService;
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
 * @since 2021-11-15
 */
@Api(value = "用户评论举报操作API")
@RestController
@RequestMapping("/u-report")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UReportController {
  
  private final UReportService uReportService;

  @SystemLog
  @PutMapping("/add")
  @ApiOperation(value = "新建用户评论举报", notes = "需要传入UReport对象， 添加成功 1， 添加失败 0")
  @PreAuthorize("hasAnyAuthority('user,admin,root')")
  public Q addReport(@Validated({CreateValidGroup.class}) @RequestBody UReport uReport) {
    Integer add = uReportService.addReport(uReport);
    if (add == 0) {
      return Q.error(500, "评论举报失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", add)).put("msg", "评论举报成功");
    }
  }

  @SystemLog
  @DeleteMapping("/delete")
  @ApiOperation(value = "删除用户评论举报", notes = "需要传入uReportId， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('user,admin,root')")
  public Q removeReport(Long uReportId) {
    Integer remove = uReportService.removeReport(uReportId);
    if (remove == 0) {
      return Q.error(500, "删除评论举报失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", remove)).put("msg", "删除评论举报成功");
    }
  }

  @SystemLog
  @PostMapping("/handle")
  @ApiOperation(value = "处理用户评论举报", notes = "需要传入UReport对象， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q handleReport(Authentication authentication, @Validated({UpdateValidGroup.class}) @RequestBody UReport uReport) {
    Integer handle = uReportService.handleReport(authentication, uReport);
    if (handle == 0) {
      return Q.error(500, "处理用户评论举报失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", handle)).put("msg", "处理用户评论举报成功");
    }
  }

  @SystemLog
  @PostMapping("/update")
  @ApiOperation(value = "修改用户评论举报信息", notes = "需要传入UReport对象， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q updateReport(Authentication authentication, @Validated({UpdateValidGroup.class}) @RequestBody UReport uReport) {
    Integer update = uReportService.updateReport(authentication, uReport);
    if (update == 0) {
      return Q.error(500, "修改用户评论举报失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", update)).put("msg", "修改用户评论举报成功");
    }
  }

  @SystemLog
  @GetMapping("/getReport")
  @ApiOperation(value = "获取评论举报数据", notes = "需要传入uReportId，返回该评论举报数据")
  @PreAuthorize("hasAnyAuthority('user,admin,root')")
  public Q getReport(Long uReportId) {
    return uReportService.getReport(uReportId);
  }

  @SystemLog
  @GetMapping("/getUserReport")
  @ApiOperation(value = "获取用户的评论举报数据", notes = "需要传入userId，返回该用户的评论举报数据")
  @PreAuthorize("hasAnyAuthority('user,admin,root')")
  public Q getUserReport(Long userId) {
    return uReportService.getUserReports(userId);
  }

  @SystemLog
  @GetMapping("/getHandlerReport")
  @ApiOperation(value = "按照处理情况获取评论举报数据", notes = "需要传入pageNumber 页码；pageSize 每页个数；handle （处理1，未处理0），返回评论举报数据")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q getHandlerReport(Integer pageNumber, Integer pageSize, Short handle) {
    return uReportService.getHandlerReports(pageNumber, pageSize, handle);
  }

  @SystemLog
  @GetMapping("/getAllReport")
  @ApiOperation(value = "获取评论举报数据", notes = "需要传入pageNumber 页码；pageSize 每页个数；condition 模糊查询条件，返回所有评论举报数据")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q getAllReport(Integer pageNumber, Integer pageSize, String condition) {
    return uReportService.getAllReports(pageNumber, pageSize, condition);
  }
}

