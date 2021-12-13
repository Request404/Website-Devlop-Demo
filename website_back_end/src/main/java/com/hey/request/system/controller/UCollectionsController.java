package com.hey.request.system.controller;


import com.hey.request.system.annotation.SystemLog;
import com.hey.request.system.entity.UCollections;
import com.hey.request.system.service.UCollectionsService;
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
@Api(value = "用户收藏操作API")
@RestController
@RequestMapping("/u-collections")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UCollectionsController {

  private final UCollectionsService uCollectionsService;

  @SystemLog
  @PutMapping("/add")
  @ApiOperation(value = "添加用户收藏记录", notes = "需要传入UCollections对象， 添加成功 1， 添加失败 0，收藏重复-1")
  @PreAuthorize("hasAnyAuthority('user,admin,root')")
  public Q addCollection(@Validated({CreateValidGroup.class}) @RequestBody UCollections uCollections) {
    Integer add = uCollectionsService.addCollection(uCollections);
    if (add == 0) {
      return Q.error(500, "收藏失败");
    } else if (add == -1) {
      return Q.error(500, "收藏失败，重复收藏");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", add)).put("msg", "收藏成功");
    }
  }

  @SystemLog
  @DeleteMapping("/delete")
  @ApiOperation(value = "删除用户收藏记录", notes = "需要传入UCollections对象， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('user,admin,root')")
  public Q removeCollection(UCollections uCollections) {
    Integer remove = uCollectionsService.removeCollection(uCollections);
    if (remove == 0) {
      return Q.error(500, "取消收藏失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", remove)).put("msg", "取消收藏成功");
    }
  }

  @SystemLog
  @GetMapping("/getUserCollections")
  @ApiOperation(value = "获取用户收藏记录", notes = "需要传入userId，返回用户所有收藏记录")
  @PreAuthorize("hasAnyAuthority('user,admin,root')")
  public Q getUserCollections(Long userId) {
    return uCollectionsService.getUserCollections(userId);
  }

  @SystemLog
  @GetMapping("/getAllCollections")
  @ApiOperation(value = "获取收藏记录数据", notes = "需要传入pageNumber 页码；pageSize 每页个数；condition 模糊查询条件，返回所有收藏记录数据")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q getAllCollections(Integer pageNumber, Integer pageSize, Integer condition) {
    return uCollectionsService.getAllCollections(pageNumber, pageSize, condition);
  }
}

