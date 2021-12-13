package com.hey.request.system.service;

import com.hey.request.system.entity.DRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hey.request.system.utils.Q;
import org.springframework.security.core.Authentication;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Request404
 * @since 2021-11-28
 */
public interface DRecordService extends IService<DRecord> {

  /**
   * 添加推荐记录
   *
   * @param dRecord DRecord对象
   * @return int
   */
  Integer addRecommendRecord(Authentication authentication, DRecord dRecord);

  /**
   * 删除推荐记录
   *
   * @param dRecordId 记录编号
   * @return int
   */
  Integer removeRecommendRecord(Long dRecordId);

  /**
   * 更新推荐记录
   *
   * @param dRecord DRecord对象
   * @return int
   */
  Integer updateRecommendRecord(Authentication authentication, DRecord dRecord);

  /**
   * 推荐记录下架
   *
   * @param recordId 推荐记录ID
   * @return int
   */
  Integer getOffRecommendRecord(Long recordId);

  /**
   * 获取全部推荐记录
   *
   * @param pageNumber 页码
   * @param pageSize   每页个数
   * @param condition  模糊查询条件
   * @return Q
   */
  Q getRecommendRecord(Integer pageNumber, Integer pageSize, Integer condition);

  /**
   * 按产品获取推荐记录
   *
   * @param productType 产品类型
   * @param needCover 只需要有宣传页面的数据
   * @return Q
   */
  Q getRecommendRecordByProductType(Short productType, Integer needCover);

  /**
   * 获取四个推荐记录
   *
   * @return Q
   */
  Q getIndexCubeRecommend();

}
