package com.hey.request.system.controller;


import com.hey.request.system.annotation.SystemLog;
import com.hey.request.system.entity.DMovies;
import com.hey.request.system.service.DMoviesService;
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
@Api(value = "电影数据操作API")
@RestController
@RequestMapping("/d-movies")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DMoviesController {

  private final DMoviesService dMoviesService;

  @SystemLog
  @PutMapping("/add")
  @ApiOperation(value = "添加电影数据", notes = "需要传入DMovies对象， 添加成功 1， 添加失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q addMovie(Authentication authentication, @Validated({CreateValidGroup.class}) @RequestBody DMovies dMovies) {
    Integer add = dMoviesService.addMovie(authentication, dMovies);
    if (add == 0) {
      return Q.error(500, "电影添加失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", add)).put("msg", "电影添加成功");
    }
  }

  @SystemLog
  @DeleteMapping("/delete")
  @ApiOperation(value = "删除电影数据", notes = "需要传入MovieId， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q removeMovie(Long MovieId) {
    Integer remove = dMoviesService.removeDMovie(MovieId);
    if (remove == 0) {
      return Q.error(500, "电影删除失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", remove)).put("msg", "电影删除成功");
    }
  }

  @SystemLog
  @DeleteMapping("/off")
  @ApiOperation(value = "下架电影数据", notes = "需要传入MovieId， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q getOffMovie(Long MovieId) {
    Integer getOff = dMoviesService.getOffMovie(MovieId);
    if (getOff == 0) {
      return Q.error(500, "电影下架失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", getOff)).put("msg", "电影下架成功");
    }
  }

  @SystemLog
  @PostMapping("/update")
  @ApiOperation(value = "更新电影数据", notes = "需要传入DMovies对象， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q updateMovie(Authentication authentication, @Validated({UpdateValidGroup.class}) @RequestBody DMovies dMovies) {
    Integer update = dMoviesService.updateMovie(authentication, dMovies);
    if (update == 0) {
      return Q.error(500, "电影更新失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", update)).put("msg", "电影更新成功");
    }
  }


  @SystemLog
  @GetMapping("/website/open/getInfo")
  @ApiOperation(value = "获取电影信息", notes = "需要传入MovieId， 返回电影信息数据")
  public Q getMovieInfo(Long MovieId) {
    return dMoviesService.getMovieInfo(MovieId);
  }

  @SystemLog
  @GetMapping("/getAllMovies")
  @ApiOperation(value = "获取所有电影数据", notes = "需要传入pageNumber 页码；pageSize 每页个数；condition 模糊查询条件，返回所有电影数据")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q getAllMovies(Integer pageNumber, Integer pageSize, String condition) {
    return dMoviesService.getAllMovies(pageNumber, pageSize, condition);
  }

  @SystemLog
  @GetMapping("/website/open/clientGetAllMovies")
  @ApiOperation(value = "客户端获取电影数据", notes = "需要传入pageNumber 页码；pageSize 每页个数；condition 模糊查询条件，返回未被下架的电影数据")
  Q clientGetAllMovies(Integer pageNumber, Integer pageSize, String condition) {
    return dMoviesService.clientGetAllMovies(pageNumber, pageSize, condition);
  }

  @SystemLog
  @GetMapping("/website/open/getWebsiteIndexMovies")
  @ApiOperation(value = "获取客户端主页电影", notes = "返回获取客户端主页电影数据")
  Q getWebsiteIndexMovies(Integer needCover) {
    if(needCover == null){
      needCover = 0;
    }
    return dMoviesService.getWebsiteIndexMovies(needCover);
  }
}

