package com.hey.request.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hey.request.system.entity.UCollections;
import com.hey.request.system.mapper.UCollectionsMapper;
import com.hey.request.system.service.UCollectionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hey.request.system.utils.Q;
import com.hey.request.system.utils.Utils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Request404
 * @since 2021-11-15
 */
@Service
public class UCollectionsServiceImpl extends ServiceImpl<UCollectionsMapper, UCollections> implements UCollectionsService {

  @Override
  public Integer addCollection(UCollections uCollections) {
    if (!this.isCollection(uCollections)) {
      uCollections.setProductType(Utils.productType(uCollections.getProductId()));
      uCollections.setCollectTime(Utils.GenerateDatetime());
      return this.baseMapper.insert(uCollections);
    } else {
      return -1;
    }
  }

  @Override
  public Integer removeCollection(UCollections uCollections) {
    return this.baseMapper.delete(new QueryWrapper<UCollections>().lambda().eq(UCollections::getUserId, uCollections.getUserId()).eq(UCollections::getProductId, uCollections.getProductId()));
  }

  @Override
  public Q getUserCollections(Long userId) {
    return Q.ok().put("data", this.baseMapper.selectList(new QueryWrapper<UCollections>().lambda().eq(UCollections::getUserId, userId).eq(UCollections::getState, 1)));
  }

  @Override
  public Q getAllCollections(Integer pageNumber, Integer pageSize, Integer condition) {
    if (condition == 0) {
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.selectList(new QueryWrapper<UCollections>().orderByDesc("collect_time")));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), new QueryWrapper<UCollections>().orderByDesc("collect_time")));
      }
    } else {
      QueryWrapper<UCollections> conditionQueryWrapper = new QueryWrapper<UCollections>()
          .and(qw -> qw.like("user_id", condition).or()
              .like("product_id", condition).or()
              .like("product_type", condition))
          .orderByDesc("collect_time");
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.selectList(conditionQueryWrapper));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), conditionQueryWrapper));
      }
    }
  }

  private Boolean isCollection(UCollections uCollections) {
    return this.baseMapper.selectOne(new QueryWrapper<UCollections>().lambda().eq(UCollections::getUserId, uCollections.getUserId()).eq(UCollections::getProductId, uCollections.getProductId())) != null;
  }
}
