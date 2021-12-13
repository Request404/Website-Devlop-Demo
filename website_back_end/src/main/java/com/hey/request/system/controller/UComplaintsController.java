package com.hey.request.system.controller;


import com.hey.request.system.annotation.SystemLog;
import com.hey.request.system.entity.UComplaints;
import com.hey.request.system.service.UComplaintsService;
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
@Api(value = "用户投诉反馈操作API")
@RestController
@RequestMapping("/u-complaints")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UComplaintsController {

  private final UComplaintsService uComplaintsService;

  @SystemLog
  @PutMapping("/add")
  @ApiOperation(value = "新建用户投诉", notes = "需要传入UComplaints对象， 添加成功 1， 添加失败 0")
  @PreAuthorize("hasAnyAuthority('user,admin,root')")
  public Q addComplaint(@Validated({CreateValidGroup.class}) @RequestBody UComplaints uComplaints) {
    Integer add = uComplaintsService.addComplaint(uComplaints);
    if (add == 0) {
      return Q.error(500, "投诉发送失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", add)).put("msg", "投诉发送成功");
    }
  }

  @SystemLog
  @DeleteMapping("/delete")
  @ApiOperation(value = "删除用户投诉", notes = "需要传入uComplaintId， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('user,admin,root')")
  public Q removeComplaint(Long uComplaintId) {
    Integer remove = uComplaintsService.removeComplaint(uComplaintId);
    if (remove == 0) {
      return Q.error(500, "删除投诉失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", remove)).put("msg", "删除投诉成功");
    }
  }

  @SystemLog
  @PostMapping("/handle")
  @ApiOperation(value = "处理用户投诉", notes = "需要传入UComplaints对象， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q handleComplaint(Authentication authentication, @Validated({UpdateValidGroup.class}) @RequestBody UComplaints uComplaints) {
    Integer handle = uComplaintsService.handleComplaint(authentication, uComplaints);
    if (handle == 0) {
      return Q.error(500, "处理用户投诉失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", handle)).put("msg", "处理用户投诉成功");
    }
  }

  @SystemLog
  @PostMapping("/update")
  @ApiOperation(value = "修改用户投诉信息", notes = "需要传入UComplaints对象， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q updateComplaint(Authentication authentication, @Validated({UpdateValidGroup.class}) @RequestBody UComplaints uComplaints) {
    Integer update = uComplaintsService.updateComplaint(authentication, uComplaints);
    if (update == 0) {
      return Q.error(500, "修改用户投诉失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", update)).put("msg", "修改用户投诉成功");
    }
  }

  @SystemLog
  @GetMapping("/getComplaint")
  @ApiOperation(value = "获取投诉数据", notes = "需要传入uComplaintId，返回该投诉数据")
  @PreAuthorize("hasAnyAuthority('user,admin,root')")
  public Q getComplaint(Long uComplaintId) {
    return uComplaintsService.getComplaint(uComplaintId);
  }

  @SystemLog
  @GetMapping("/getUserComplaints")
  @ApiOperation(value = "获取用户的投诉数据", notes = "需要传入userId，返回该用户的投诉数据")
  @PreAuthorize("hasAnyAuthority('user,admin,root')")
  public Q getUserComplaints(Long userId) {
    return uComplaintsService.getUserComplaints(userId);
  }

  @SystemLog
  @GetMapping("/getHandlerComplaints")
  @ApiOperation(value = "按照处理情况获取投诉数据", notes = "需要传入pageNumber 页码；pageSize 每页个数；handle （处理1，未处理0），返回投诉数据")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q getHandlerComplaints(Integer pageNumber, Integer pageSize, Short handle) {
    return uComplaintsService.getHandlerComplaints(pageNumber, pageSize, handle);
  }

  @SystemLog
  @GetMapping("/getAllComplaints")
  @ApiOperation(value = "获取投诉数据", notes = "需要传入pageNumber 页码；pageSize 每页个数；condition 模糊查询条件，返回所有投诉数据")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q getAllComplaints(Integer pageNumber, Integer pageSize, String condition){
    return uComplaintsService.getAllComplaints(pageNumber, pageSize, condition);
  }
}

