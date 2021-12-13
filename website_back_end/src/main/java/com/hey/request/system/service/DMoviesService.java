package com.hey.request.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hey.request.system.entity.DMovies;
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
public interface DMoviesService extends IService<DMovies> {

  /**
   * 添加电影数据
   *
   * @param dMovies DMovies对象
   * @return int
   */
  Integer addMovie(Authentication authentication, DMovies dMovies);

  /**
   * 删除电影
   *
   * @param id 电影编号
   * @return int
   */
  Integer removeDMovie(Long id);

  /**
   * 下架电影
   *
   * @param id 电影编号
   * @return int
   */
  Integer getOffMovie(Long id);

  /**
   * 更新电影
   *
   * @param dMovies DMovies对象
   * @return int
   */
  Integer updateMovie(Authentication authentication, DMovies dMovies);

  /**
   * 获取一部电影信息
   *
   * @param id 电影编号
   * @return DBooks
   */
  Q getMovieInfo(Long id);

  /**
   * 获取电影数据
   *
   * @param pageNumber 页码
   * @param pageSize   每页个数
   * @param condition  模糊查询条件
   * @return Q
   */
  Q getAllMovies(Integer pageNumber, Integer pageSize, String condition);

  /**
   * 客户端获取电影数据
   *
   * @param pageNumber 页码
   * @param pageSize   每页个数
   * @param condition  模糊查询条件
   * @return Q
   */
  Q clientGetAllMovies(Integer pageNumber, Integer pageSize, String condition);

  /**
   * 获取推荐列表
   *
   * @param userId 用户ID
   * @return Q
   */
  Q getRecommendsMovies(Integer pageNumber, Integer pageSize, Long userId);


  /**
   * 获取每日主页推荐
   *
   * @return Q
   */
  Q getWebsiteIndexMovies(Integer needCover);

}
