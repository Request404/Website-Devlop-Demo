package com.hey.request.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hey.request.system.entity.DBooks;
import com.hey.request.system.utils.Q;
import org.springframework.security.core.Authentication;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Request404
 * @since 2021-11-15
 */
public interface DBooksService extends IService<DBooks> {

  /**
   * 添加书籍数据
   *
   * @param dBooks 书籍对象
   * @return int
   */
  Integer addBook(Authentication authentication, DBooks dBooks);

  /**
   * 删除书籍对象
   *
   * @param id 书籍编号
   * @return int
   */
  Integer removeBook(Long id);

  /**
   * 下架书籍
   *
   * @param id 书籍编号
   * @return int
   */
  Integer getOffBook(Long id);

  /**
   * 更新书籍
   *
   * @param dBooks 书籍对象
   * @return int
   */
  Integer updateBook(Authentication authentication,DBooks dBooks);

  /**
   * 获取一本书籍信息
   *
   * @param id 书籍编号
   * @return DBooks
   */
  Q getBookInfo(Long id);

  /**
   * 获取图书数据
   *
   * @param pageNumber 页码
   * @param pageSize   每页个数
   * @param condition  模糊查询条件
   * @return Q
   */
  Q getAllBooks(Integer pageNumber, Integer pageSize, String condition);

  /**
   * 客户端获取图书数据
   *
   * @param pageNumber 页码
   * @param pageSize   每页个数
   * @param condition  模糊查询条件
   * @return Q
   */
  Q clientGetAllBooks(Integer pageNumber, Integer pageSize, String condition);

  /**
   * 获取推荐列表
   *
   * @param userId 用户ID
   * @return Q
   */
  Q getRecommendsBooks(Integer pageNumber, Integer pageSize, Long userId);

  /**
   * 获取每日主页推荐
   *
   * @return Q
   */
  Q getWebsiteIndexBooks(Integer needCover);

}
