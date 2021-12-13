package com.hey.request.system.service;

import com.hey.request.system.entity.UScore;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hey.request.system.utils.Q;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Request404
 * @since 2021-11-15
 */
public interface UScoreService extends IService<UScore> {

  /**
   * 添加分数统计信息
   *
   * @param score UScore对象
   * @return int
   */
  Integer addProductScore(UScore score);

  /**
   * 删除分数统计信息
   *
   * @param productId 产品编号
   * @return int
   */
  Integer removeProductScore(Long productId);

  /**
   * 更新分数统计信息
   *
   * @param score UScore对象
   * @return int
   */
  Integer updateProductScore(UScore score);

  /**
   * 访问记录
   *
   * @param productId 产品编号
   * @return int
   */
  Integer visitRecord(Long productId);

  /**
   * 获取分数统计
   *
   * @param productId 产品编号
   * @return UScore
   */
  UScore getScore(Long productId);

  /**
   * 按照分数统计
   *
   * @param pageNumber  页码
   * @param pageSize    每页个数
   * @param productType 产品类型
   * @return Q
   */
  Q getScoreStatistical(Integer pageNumber, Integer pageSize, Integer productType);

  /**
   * 按照其他条件统计
   *
   * @param pageNumber  页码
   * @param pageSize    每页个数
   * @param productType 产品类型
   * @return Q
   */
  Q getOtherStatistical(Integer pageNumber, Integer pageSize, Integer productType);

  /**
   * 获取新上架产品
   *
   * @param pageNumber  页码
   * @param pageSize    每页个数
   * @param productType 产品类型
   * @return Q
   */
  Q getNewProduction(Integer pageNumber, Integer pageSize, Integer productType);

  /**
   * 全部条件统计
   *
   * @param pageNumber  页码
   * @param pageSize    每页个数
   * @param productType 产品类型
   * @return Q
   */
  Q getTotalStatistical(Integer pageNumber, Integer pageSize, Integer productType);

  /**
   * 获取举报信息数据
   *
   * @param pageNumber 页码
   * @param pageSize   每页个数
   * @param condition  模糊查询条件
   * @return Q
   */
  Q getAllScoreData(Integer pageNumber, Integer pageSize, Integer condition);
}
