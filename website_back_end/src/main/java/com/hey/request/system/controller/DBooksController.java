package com.hey.request.system.controller;


import com.hey.request.system.annotation.SystemLog;
import com.hey.request.system.entity.DBooks;
import com.hey.request.system.service.DBooksService;
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
@Api(value = "书籍数据操作API")
@RestController
@RequestMapping("/d-books")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DBooksController {

  private final DBooksService dBooksService;

  @SystemLog
  @PutMapping("/add")
  @ApiOperation(value = "添加书籍数据", notes = "需要传入DBooks对象， 添加成功 1， 添加失败 0， 书籍重复 -1")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q addBook(Authentication authentication, @Validated({CreateValidGroup.class}) @RequestBody DBooks dBooks) {
    Integer add = dBooksService.addBook(authentication, dBooks);
    if (add == 0) {
      return Q.error(500, "书籍添加失败");
    } else if (add == -1) {
      return Q.error(500, "书籍数据重复");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", add)).put("msg", "书籍添加成功");
    }
  }

  @SystemLog
  @DeleteMapping("/delete")
  @ApiOperation(value = "删除书籍数据", notes = "需要传入bookId， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q removeBook(Long bookId) {
    Integer remove = dBooksService.removeBook(bookId);
    if (remove == 0) {
      return Q.error(500, "书籍删除失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", remove)).put("msg", "书籍删除成功");
    }
  }

  @SystemLog
  @DeleteMapping("/off")
  @ApiOperation(value = "下架书籍数据", notes = "需要传入bookId， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q getOffBook(Long bookId) {
    Integer getOff = dBooksService.getOffBook(bookId);
    if (getOff == 0) {
      return Q.error(500, "书籍下架失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", getOff)).put("msg", "书籍下架成功");
    }
  }

  @SystemLog
  @PostMapping("/update")
  @ApiOperation(value = "更新书籍数据", notes = "需要传入DBooks对象， 成功 1， 失败 0")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q updateBook(Authentication authentication, @Validated({UpdateValidGroup.class}) @RequestBody DBooks dBooks) {
    Integer update = dBooksService.updateBook(authentication, dBooks);
    if (update == 0) {
      return Q.error(500, "书籍更新失败");
    } else {
      return Objects.requireNonNull(Q.ok().put("data", update)).put("msg", "书籍更新成功");
    }
  }

  @SystemLog
  @GetMapping("/website/open/getInfo")
  @ApiOperation(value = "获取书籍信息", notes = "需要传入bookId， 返回书籍信息数据")
  public Q getBookInfo(Long bookId) {
    return dBooksService.getBookInfo(bookId);
  }

  @SystemLog
  @GetMapping("/getAllBooks")
  @ApiOperation(value = "获取所有书籍数据", notes = "需要传入pageNumber 页码；pageSize 每页个数；condition 模糊查询条件，返回所有书籍数据")
  @PreAuthorize("hasAnyAuthority('admin,root')")
  public Q getAllBooks(Integer pageNumber, Integer pageSize, String condition) {
    return dBooksService.getAllBooks(pageNumber, pageSize, condition);
  }

  @SystemLog
  @GetMapping("/website/open/clientGetAllBooks")
  @ApiOperation(value = "客户端获取书籍数据", notes = "需要传入pageNumber 页码；pageSize 每页个数；condition 模糊查询条件，返回未被下架的书籍数据")
  Q clientGetAllBooks(Integer pageNumber, Integer pageSize, String condition) {
    return dBooksService.clientGetAllBooks(pageNumber, pageSize, condition);
  }

  @SystemLog
  @GetMapping("/website/open/getWebsiteIndexBooks")
  @ApiOperation(value = "获取客户端主页书籍", notes = "返回获取客户端主页书籍数据")
  Q getWebsiteIndexBooks(Integer needCover) {
    if(needCover == null){
      needCover = 0;
    }
    return dBooksService.getWebsiteIndexBooks(needCover);
  }

}

