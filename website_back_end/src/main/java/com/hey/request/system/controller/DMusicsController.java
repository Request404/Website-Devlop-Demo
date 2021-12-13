package com.hey.request.system.controller;


import com.hey.request.system.annotation.SystemLog;
import com.hey.request.system.entity.DMusics;
import com.hey.request.system.service.DMusicsService;
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
 *  前端控制器
 * </p>
 *
 * @author Request404
 * @since 2021-11-15
 */
@Api(value = "音乐数据操作API")
@RestController
@RequestMapping("/d-musics")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DMusicsController {
  
  private final DMusicsService dMusicsService;

  @SystemLog
  @PutMapping("/add")
  @ApiOperation(value = "添加音乐数据", notes = "需要传入DMusics对象， 添加成功 1， 添加失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q addMusic(Authentication authentication, @Validated({CreateValidGroup.class}) @RequestBody DMusics dMusics) {
    Integer add = dMusicsService.addMusic(authentication, dMusics);
    if (add == 0) {
      return Q.error(500, "音乐添加失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", add)).put("msg", "音乐添加成功");
    }
  }

  @SystemLog
  @DeleteMapping("/delete")
  @ApiOperation(value = "删除音乐数据", notes = "需要传入MusicId， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q removeMusic(Long MusicId) {
    Integer remove = dMusicsService.removeDMusic(MusicId);
    if (remove == 0) {
      return Q.error(500, "音乐删除失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", remove)).put("msg", "音乐删除成功");
    }
  }

  @SystemLog
  @DeleteMapping("/off")
  @ApiOperation(value = "下架音乐数据", notes = "需要传入MusicId， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q getOffMusic(Long MusicId) {
    Integer getOff = dMusicsService.getOffMusic(MusicId);
    if (getOff == 0) {
      return Q.error(500, "音乐下架失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", getOff)).put("msg", "音乐下架成功");
    }
  }

  @SystemLog
  @PostMapping("/update")
  @ApiOperation(value = "更新音乐数据", notes = "需要传入DMusics对象， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q updateMusic(Authentication authentication, @Validated({UpdateValidGroup.class}) @RequestBody DMusics dMusics) {
    Integer update = dMusicsService.updateMusic(authentication, dMusics);
    if (update == 0) {
      return Q.error(500, "音乐更新失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", update)).put("msg", "音乐更新成功");
    }
  }


  @SystemLog
  @GetMapping("/website/open/getInfo")
  @ApiOperation(value = "获取音乐信息", notes = "需要传入MusicId， 返回音乐信息数据")
  public Q getMusicInfo(Long MusicId) {
    return dMusicsService.getMusicInfo(MusicId);
  }

  @SystemLog
  @GetMapping("/getAllMusics")
  @ApiOperation(value = "获取所有音乐数据", notes = "需要传入pageNumber 页码；pageSize 每页个数；condition 模糊查询条件，返回所有音乐数据")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q getAllMusics(Integer pageNumber, Integer pageSize, String condition) {
    return dMusicsService.getAllMusics(pageNumber, pageSize, condition);
  }

  @SystemLog
  @GetMapping("/website/open/clientGetAllMusics")
  @ApiOperation(value = "客户端获取音乐数据", notes = "需要传入pageNumber 页码；pageSize 每页个数；condition 模糊查询条件，返回未被下架的音乐数据")
  Q clientGetAllMusics(Integer pageNumber, Integer pageSize, String condition) {
    return dMusicsService.clientGetAllMusics(pageNumber, pageSize, condition);
  }

  @SystemLog
  @GetMapping("/website/open/getWebsiteIndexMusics")
  @ApiOperation(value = "获取客户端主页音乐", notes = "返回获取客户端主页音乐数据")
  Q getWebsiteIndexMusics(Integer needCover) {
    if(needCover == null){
      needCover = 0;
    }
    return dMusicsService.getWebsiteIndexMusics(needCover);
  }
}

