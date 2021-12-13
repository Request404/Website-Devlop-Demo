package com.hey.request.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hey.request.security.entity.SysUserDetails;
import com.hey.request.system.entity.UComplaints;
import com.hey.request.system.mapper.UComplaintsMapper;
import com.hey.request.system.service.UComplaintsService;
import com.hey.request.system.utils.Q;
import com.hey.request.system.utils.Utils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Request404
 * @since 2021-11-15
 */
@Service
public class UComplaintsServiceImpl extends ServiceImpl<UComplaintsMapper, UComplaints> implements UComplaintsService {

  @Override
  public Integer addComplaint(UComplaints uComplaints) {
    return this.baseMapper.insert(uComplaints);
  }

  @Override
  public Integer removeComplaint(Long id) {
    return this.baseMapper.deleteById(id);
  }

  @Override
  public Integer handleComplaint(Authentication authentication, UComplaints uComplaints) {
    SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
    uComplaints.setHandler(userDetails.getUsername());
    uComplaints.setOperationUser(userDetails.getUsername());
    uComplaints.setOperationTime(Utils.GenerateDatetime());
    uComplaints.setState((short) 1);
    return this.baseMapper.updateById(uComplaints);
  }

  @Override
  public Integer updateComplaint(Authentication authentication, UComplaints uComplaints) {
    SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
    uComplaints.setOperationUser(userDetails.getUsername());
    uComplaints.setOperationTime(Utils.GenerateDatetime());
    return this.baseMapper.updateById(uComplaints);
  }

  @Override
  public Q getComplaint(Long id) {
    return Q.ok().put("data", this.baseMapper.selectById(id));
  }

  @Override
  public Q getUserComplaints(Long userId) {
    return Q.ok().put("data", this.baseMapper.selectList(new QueryWrapper<UComplaints>().lambda().eq(UComplaints::getUserId, userId)));
  }

  @Override
  public Q getHandlerComplaints(Integer pageNumber, Integer pageSize, Short handle) {
    if (pageNumber == 0 && pageSize == 0) {
      return Q.ok().put("data", this.baseMapper.selectList(new QueryWrapper<UComplaints>().lambda().eq(UComplaints::getState, handle)));
    } else {
      return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), new QueryWrapper<UComplaints>().lambda().eq(UComplaints::getState, handle)));
    }
  }

  @Override
  public Q getAllComplaints(Integer pageNumber, Integer pageSize, String condition) {
    if (Objects.equals(condition, "")) {
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.selectList(new QueryWrapper<UComplaints>().orderByAsc("id")));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), new QueryWrapper<UComplaints>().orderByAsc("id")));
      }
    } else {
      QueryWrapper<UComplaints> conditionQueryWrapper = new QueryWrapper<UComplaints>()
          .and(qw -> qw.like("complaint_content", condition).or()
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
