package com.hey.request.system.controller;


import com.hey.request.system.annotation.SystemLog;
import com.hey.request.system.entity.UComments;
import com.hey.request.system.service.UCommentsService;
import com.hey.request.system.utils.Q;
import com.hey.request.system.utils.group.CreateValidGroup;
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
@Api(value = "用户评论操作API")
@RestController
@RequestMapping("/u-comments")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UCommentsController {

  private final UCommentsService uCommentsService;

  @SystemLog
  @PutMapping("/add")
  @ApiOperation(value = "添加用户评论", notes = "需要传入UComments对象， 添加成功 1， 添加失败 0，评论重复-1")
  @PreAuthorize("hasAnyAuthority('user,admin,root')")
  public Q addComments(@Validated({CreateValidGroup.class}) @RequestBody UComments uComments) {
    Integer add = uCommentsService.addComments(uComments);
    if (add == 0) {
      return Q.error(500, "评论失败");
    } else if (add == -1) {
      return Q.error(500, "评论失败，该用户已评论");
    } else if (add == -2) {
      return Q.error(500, "评分应该在1-5之间");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", add)).put("msg", "评论成功");
    }
  }

  @SystemLog
  @DeleteMapping("/delete")
  @ApiOperation(value = "删除用户评论记录", notes = "需要传入commentId， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('user,admin,root')")
  public Q removeComments(Long commentId) {
    Integer remove = uCommentsService.removeComments(commentId);
    if (remove == 0) {
      return Q.error(500, "删除评论失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", remove)).put("msg", "删除评论成功");
    }
  }


  @SystemLog
  @DeleteMapping("/off")
  @ApiOperation(value = "屏蔽用户评论", notes = "需要传入commentId， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q getOffComments(Long commentId) {
    Integer getOff = uCommentsService.getOffComments(commentId);
    if (getOff == 0) {
      return Q.error(500, "用户评论屏蔽失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", getOff)).put("msg", "用户评论屏蔽功");
    }
  }

  @SystemLog
  @GetMapping("/website/open/getProductionComments")
  @ApiOperation(value = "获取某个文化产品的评论数据", notes = "需要传入productId 产品编号，pageNumber 页码；pageSize 每页个数，返回该文化产品的评论数据")
  public Q getProductionComments(Long productId, Integer pageNumber, Integer pageSize) {
    return uCommentsService.getProductionComments(productId, pageNumber, pageSize);
  }

  @SystemLog
  @GetMapping("/getUserComments")
  @ApiOperation(value = "获取用户的评论数据", notes = "需要传入userId，返回该用户的评论数据")
  @PreAuthorize("hasAnyAuthority('user,admin,root')")
  public Q getUserComments(Long userId) {
    return uCommentsService.getUserComments(userId);
  }

  @SystemLog
  @GetMapping("/getAllComments")
  @ApiOperation(value = "获取评论数据", notes = "需要传入pageNumber 页码；pageSize 每页个数；condition 模糊查询条件，返回所有评论数据")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  Q getAllComments(Integer pageNumber, Integer pageSize, Integer condition) {
    return uCommentsService.getAllComments(pageNumber, pageSize, condition);
  }
}

