package com.hey.request.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hey.request.system.entity.UReport;
import com.hey.request.system.utils.Q;
import org.springframework.security.core.Authentication;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Request404
 * @since 2021-11-15
 */
public interface UReportService extends IService<UReport> {

  /**
   * 添加举报信息
   *
   * @param uReport UReport对象
   * @return int
   */
  Integer addReport(UReport uReport);

  /**
   * 删除举报信息
   *
   * @param id 举报信息编号
   * @return int
   */
  Integer removeReport(Long id);

  /**
   * 处理举报信息
   *
   * @param uReport UReport对象
   * @return int
   */
  Integer handleReport(Authentication authentication, UReport uReport);


  /**
   * 更新举报信息
   *
   * @param uReport UReport对象
   * @return int
   */
  Integer updateReport(Authentication authentication, UReport uReport);

  /**
   * 获取单条举报信息
   *
   * @param id 举报信息编号
   * @return int
   */
  Q getReport(Long id);

  /**
   * 查询单个用户举报信息
   *
   * @param userId 用户编号
   * @return Q
   */
  Q getUserReports(Long userId);

  /**
   * 按照处理状态查询举报信息
   *
   * @param handle 1 or 0
   * @return Q
   */
  Q getHandlerReports(Integer pageNumber, Integer pageSize, Short handle);

  /**
   * 获取举报信息数据
   *
   * @param pageNumber 页码
   * @param pageSize   每页个数
   * @param condition  模糊查询条件
   * @return Q
   */
  Q getAllReports(Integer pageNumber, Integer pageSize, String condition);
}
