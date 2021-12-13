package com.hey.request.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hey.request.system.entity.UComments;
import com.hey.request.system.utils.Q;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Request404
 * @since 2021-11-15
 */
public interface UCommentsService extends IService<UComments> {

  /**
   * 添加评论
   *
   * @param uComments UComments对象
   * @return int
   */
  Integer addComments(UComments uComments);

  /**
   * 删除评论
   *
   * @param id 评论编号
   * @return int
   */
  Integer removeComments(Long id);

  /**
   * 删除评论
   *
   * @param id 评论编号
   * @return int
   */
  Integer getOffComments(Long id);

//  /**
//   * 修好评论信息
//   *
//   * @param uComments UComments对象
//   * @return int
//   */
//  Integer updateComments(UComments uComments);

  /**
   * 获取文化产品评论
   * @param productId 文化产品编号
   * @return int
   */
  Q getProductionComments(Long productId, Integer pageNumber, Integer pageSize);

  /**
   * 获取用户评论
   * @param userId 用户ID
   * @return Q
   */
  Q getUserComments(Long userId);

  /**
   * 获取评论数据
   *
   * @param pageNumber 页码
   * @param pageSize   每页个数
   * @param condition  模糊查询条件
   * @return Q
   */
  Q getAllComments(Integer pageNumber, Integer pageSize, Integer condition);
}
