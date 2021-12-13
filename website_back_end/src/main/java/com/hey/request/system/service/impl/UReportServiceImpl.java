package com.hey.request.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hey.request.security.entity.SysUserDetails;
import com.hey.request.system.entity.UComplaints;
import com.hey.request.system.entity.UReport;
import com.hey.request.system.mapper.UReportMapper;
import com.hey.request.system.service.UReportService;
import com.hey.request.system.utils.Q;
import com.hey.request.system.utils.Utils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Request404
 * @since 2021-11-15
 */
@Service
public class UReportServiceImpl extends ServiceImpl<UReportMapper, UReport> implements UReportService {

  @Override
  public Integer addReport(UReport uReport) {
    return this.baseMapper.insert(uReport);
  }

  @Override
  public Integer removeReport(Long id) {
    return this.baseMapper.deleteById(id);
  }

  @Override
  public Integer handleReport(Authentication authentication, UReport uReport) {
    SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
    uReport.setHandler(userDetails.getUsername());
    uReport.setOperationUser(userDetails.getUsername());
    uReport.setOperationTime(Utils.GenerateDatetime());
    uReport.setState((short) 1);
    return this.baseMapper.updateById(uReport);
  }

  @Override
  public Integer updateReport(Authentication authentication, UReport uReport) {
    SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
    uReport.setOperationUser(userDetails.getUsername());
    uReport.setOperationTime(Utils.GenerateDatetime());
    return this.baseMapper.updateById(uReport);
  }

  @Override
  public Q getReport(Long id) {
    return Q.ok().put("data", this.baseMapper.selectById(id));
  }

  @Override
  public Q getUserReports(Long userId) {
    return Q.ok().put("data", this.baseMapper.selectList(new QueryWrapper<UReport>().lambda().eq(UReport::getUserId, userId)));
  }

  @Override
  public Q getHandlerReports(Integer pageNumber, Integer pageSize, Short handle) {
    if (pageNumber == 0 && pageSize == 0) {
      return Q.ok().put("data", this.baseMapper.selectList(new QueryWrapper<UReport>().lambda().eq(UReport::getState, handle)));
    } else {
      return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), new QueryWrapper<UReport>().lambda().eq(UReport::getState, handle)));
    }
  }

  @Override
  public Q getAllReports(Integer pageNumber, Integer pageSize, String condition) {
    if (Objects.equals(condition, "")) {
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.selectList(new QueryWrapper<UReport>().orderByAsc("id")));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), new QueryWrapper<UReport>().orderByAsc("id")));
      }
    } else {
      QueryWrapper<UReport> conditionQueryWrapper = new QueryWrapper<UReport>()
          .and(qw -> qw.like("report_type", condition).or()
              .like("handler", condition).or()
              .like("handler_reply", condition).or()
              .like("operation_time", condition).or()
              .like("operation_user", condition))
          .orderByAsc("id");
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.selectList(conditionQueryWrapper));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), conditionQueryWrapper));
      }
    }
  }
}
