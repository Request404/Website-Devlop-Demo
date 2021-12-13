package com.hey.request.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hey.request.system.entity.UComplaints;
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
public interface UComplaintsService extends IService<UComplaints> {

  /**
   * 添加投诉信息
   *
   * @param uComplaints UComplaints对象
   * @return int
   */
  Integer addComplaint(UComplaints uComplaints);

  /**
   * 删除投诉信息
   *
   * @param id 投诉信息编号
   * @return int
   */
  Integer removeComplaint(Long id);

  /**
   * 处理投诉信息
   *
   * @param uComplaints UComplaints对象
   * @return int
   */
  Integer handleComplaint(Authentication authentication, UComplaints uComplaints);

  /**
   * 更新投诉信息
   *
   * @param uComplaints UComplaints对象
   * @return int
   */
  Integer updateComplaint(Authentication authentication, UComplaints uComplaints);

  /**
   * 获取单条投诉信息
   *
   * @param id 投诉信息编号
   * @return int
   */
  Q getComplaint(Long id);

  /**
   * 查询单个用户投诉信息
   *
   * @param userId 用户编号
   * @return Q
   */
  Q getUserComplaints(Long userId);

  /**
   * 按照处理状态查询投诉信息
   *
   * @param handle 1 or 0
   * @return Q
   */
  Q getHandlerComplaints(Integer pageNumber, Integer pageSize, Short handle);

  /**
   * 获取投诉信息数据
   *
   * @param pageNumber 页码
   * @param pageSize   每页个数
   * @param condition  模糊查询条件
   * @return Q
   */
  Q getAllComplaints(Integer pageNumber, Integer pageSize, String condition);
}
