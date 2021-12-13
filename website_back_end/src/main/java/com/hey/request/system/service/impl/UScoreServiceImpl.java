package com.hey.request.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hey.request.system.entity.UScore;
import com.hey.request.system.mapper.UScoreMapper;
import com.hey.request.system.service.UScoreService;
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
public class UScoreServiceImpl extends ServiceImpl<UScoreMapper, UScore> implements UScoreService {

  @Override
  public Integer addProductScore(UScore score) {
    return this.baseMapper.insert(score);
  }

  @Override
  public Integer removeProductScore(Long productId) {
    return this.baseMapper.delete(new QueryWrapper<UScore>().lambda().eq(UScore::getProductId, productId));
  }

  @Override
  public Integer updateProductScore(UScore score) {
    return this.baseMapper.update(score, new UpdateWrapper<UScore>().lambda().eq(UScore::getProductId, score.getProductId()));
  }

  @Override
  public Integer visitRecord(Long productId) {
    UScore score = this.getScore(productId);
    score.setTodayVisit(score.getTodayVisit() + 1);
    score.setStatisticPoint(score.getStatisticPoint() + 0.35);
    return this.baseMapper.update(score, new UpdateWrapper<UScore>().lambda().eq(UScore::getProductId, score.getProductId()));
  }

  @Override
  public UScore getScore(Long productId) {
    return this.baseMapper.selectOne(new QueryWrapper<UScore>().lambda().eq(UScore::getProductId, productId));
  }

  @Override
  public Q getScoreStatistical(Integer pageNumber, Integer pageSize, Integer productType) {
    if (productType == 0) {
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.selectList(new QueryWrapper<UScore>().lambda().orderByDesc(UScore::getScore)));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), new QueryWrapper<UScore>().lambda().orderByDesc(UScore::getScore)));
      }
    } else {
      QueryWrapper<UScore> conditionQueryWrapper = new QueryWrapper<UScore>()
          .eq("product_type", productType)
          .orderByDesc("score");
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.selectList(conditionQueryWrapper));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), conditionQueryWrapper));
      }
    }
  }

  @Override
  public Q getOtherStatistical(Integer pageNumber, Integer pageSize, Integer productType) {
    if (productType == 0) {
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.selectList(new QueryWrapper<UScore>().lambda().orderByDesc(UScore::getSevenVisit)));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), new QueryWrapper<UScore>().lambda().orderByDesc(UScore::getSevenVisit)));
      }
    } else {
      QueryWrapper<UScore> conditionQueryWrapper = new QueryWrapper<UScore>()
          .eq("product_type", productType)
          .orderByDesc("seven_visit");
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.selectList(conditionQueryWrapper));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), conditionQueryWrapper));
      }
    }
  }

  @Override
  public Q getNewProduction(Integer pageNumber, Integer pageSize, Integer productType) {
    if (productType == 0) {
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.selectList(new QueryWrapper<UScore>().lambda().between(UScore::getPostDate, Utils.GeneratePassDatetime(7), Utils.GenerateDatetime())));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), new QueryWrapper<UScore>().lambda().between(UScore::getPostDate, Utils.GeneratePassDatetime(7), Utils.GenerateDatetime())));
      }
    } else {
      QueryWrapper<UScore> conditionQueryWrapper = new QueryWrapper<UScore>()
          .eq("product_type", productType)
          .between("post_date", Utils.GeneratePassDatetime(7), Utils.GenerateDatetime());
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.selectList(conditionQueryWrapper));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), conditionQueryWrapper));
      }
    }
  }

  @Override
  public Q getTotalStatistical(Integer pageNumber, Integer pageSize, Integer productType) {
    if (productType == 0) {
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.selectList(new QueryWrapper<UScore>().lambda().orderByDesc(UScore::getStatisticPoint)));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), new QueryWrapper<UScore>().lambda().orderByDesc(UScore::getStatisticPoint)));
      }
    } else {
      QueryWrapper<UScore> conditionQueryWrapper = new QueryWrapper<UScore>()
          .eq("product_type", productType)
          .orderByDesc("statistic_point");
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.selectList(conditionQueryWrapper));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), conditionQueryWrapper));
      }
    }
  }

  @Override
  public Q getAllScoreData(Integer pageNumber, Integer pageSize, Integer condition) {
    if (condition == 0) {
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.selectList(new QueryWrapper<UScore>().orderByAsc("product_id")));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), new QueryWrapper<UScore>().orderByAsc("product_id")));
      }
    } else {
      QueryWrapper<UScore> conditionQueryWrapper = new QueryWrapper<UScore>()
          .eq("product_type", condition)
          .and(qw -> qw.like("id", condition).or()
              .like("product_id", condition).or())
          .orderByAsc("product_id");
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.selectList(conditionQueryWrapper));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), conditionQueryWrapper));
      }
    }
  }

}
