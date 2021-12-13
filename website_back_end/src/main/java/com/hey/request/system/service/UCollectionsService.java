package com.hey.request.system.service;

import com.hey.request.system.entity.UCollections;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hey.request.system.utils.Q;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Request404
 * @since 2021-11-15
 */
public interface UCollectionsService extends IService<UCollections> {

  /**
   * 添加收藏信息
   * @param uCollections UCollections对象
   * @return int
   */
  Integer addCollection(UCollections uCollections);

  /**
   * 删除收藏信息
   * @param uCollections UCollections对象
   * @return int
   */
  Integer removeCollection(UCollections uCollections);

  /**
   * 获取用户的收藏信息
   * @param userId 用户Id
   * @return Q
   */
  Q getUserCollections(Long userId);

  /**
   * 获取收藏数据
   *
   * @param pageNumber 页码
   * @param pageSize   每页个数
   * @param condition  模糊查询条件
   * @return Q
   */
  Q getAllCollections(Integer pageNumber, Integer pageSize, Integer condition);


}
