package com.hey.request.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hey.request.system.entity.DMusics;
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
public interface DMusicsService extends IService<DMusics> {

  /**
   * 添加音乐数据
   *
   * @param dMusics DMusics对象
   * @return int
   */
  Integer addMusic(Authentication authentication, DMusics dMusics);

  /**
   * 删除音乐
   *
   * @param id 音乐编号
   * @return int
   */
  Integer removeDMusic(Long id);

  /**
   * 下架音乐
   *
   * @param id 音乐编号
   * @return int
   */
  Integer getOffMusic(Long id);

  /**
   * 更新音乐信息
   *
   * @param dMusics DMusics对象
   * @return int
   */
  Integer updateMusic(Authentication authentication, DMusics dMusics);

  /**
   * 获取一首音乐信息
   *
   * @param id 音乐编号
   * @return DBooks
   */
  Q getMusicInfo(Long id);

  /**
   * 获取音乐数据
   *
   * @param pageNumber 页码
   * @param pageSize   每页个数
   * @param condition  模糊查询条件
   * @return Q
   */
  Q getAllMusics(Integer pageNumber, Integer pageSize, String condition);

  /**
   * 客户端获取音乐数据
   *
   * @param pageNumber 页码
   * @param pageSize   每页个数
   * @param condition  模糊查询条件
   * @return Q
   */
  Q clientGetAllMusics(Integer pageNumber, Integer pageSize, String condition);

  /**
   * 获取推荐列表
   *
   * @param userId 用户ID
   * @return Q
   */
  Q getRecommendsMusics(Integer pageNumber, Integer pageSize, Long userId);

  /**
   * 获取每日主页推荐
   *
   * @return Q
   */
  Q getWebsiteIndexMusics(Integer needCover);
}
