package com.hey.request.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hey.request.system.entity.UComments;
import com.hey.request.system.entity.UScore;
import com.hey.request.system.mapper.UCommentsMapper;
import com.hey.request.system.service.UCommentsService;
import com.hey.request.system.service.UScoreService;
import com.hey.request.system.utils.Q;
import com.hey.request.system.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Request404
 * @since 2021-11-15
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UCommentsServiceImpl extends ServiceImpl<UCommentsMapper, UComments> implements UCommentsService {

  private final UScoreService uScoreService;

  @Override
  @Transactional
  public Integer addComments(UComments uComments) {
    if (!this.isComments(uComments.getUserId(), uComments.getProductId())) {

      UScore score = uScoreService.getScore(uComments.getProductId());
      if (uComments.getScore() > 0 && uComments.getScore() <= 5) {
        switch (uComments.getScore()) {
          case 1:
            score.setOneStar(score.getOneStar() + 1);
            break;
          case 2:
            score.setTwoStar(score.getTwoStar() + 1);
            break;
          case 3:
            score.setThreeStar(score.getThreeStar() + 1);
            break;
          case 4:
            score.setFourStar(score.getFourStar() + 1);
            break;
          case 5:
            score.setFiveStar(score.getFiveStar() + 1);
            break;
        }
        Double point = (uComments.getScore() - 2) * 0.65;
        score.setTotal(score.getTotal() + 1L);
        score.setScore(score.getScore() + uComments.getScore());
        score.setStatisticPoint(score.getStatisticPoint() + point);

        uComments.setProductType(Utils.productType(uComments.getProductId()));
        uComments.setCommentTime(Utils.GenerateDatetime());
        int scoreUpdate = uScoreService.updateProductScore(score);
        int commentInsert = this.baseMapper.insert(uComments);
        if (scoreUpdate != 0 && commentInsert != 0) {
          return 1;
        } else {
          return 0;
        }
      } else {
        return -2;
      }
    } else {
      return -1;
    }
  }

  @Override
  public Integer removeComments(Long id) {
    return this.baseMapper.deleteById(id);
  }

  @Override
  public Integer getOffComments(Long id) {
    UComments uComments = this.baseMapper.selectById(id);
    if (uComments.getState() == 1) {
      uComments.setState((short) 0);
    } else {
      uComments.setState((short) 1);
    }

    return this.baseMapper.updateById(uComments);
  }

//  @Override
//  public Integer updateComments(UComments uComments) {
//    return this.baseMapper.updateById(uComments);
//  }

  @Override
  public Q getProductionComments(Long productId, Integer pageNumber, Integer pageSize) {
    if (pageNumber == 0 && pageSize == 0) {
      return Q.ok().put("data", this.baseMapper.selectList(new QueryWrapper<UComments>().lambda().eq(UComments::getProductId, productId).eq(UComments::getState, 1)));
    } else {
      return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), new QueryWrapper<UComments>().lambda().eq(UComments::getProductId, productId).eq(UComments::getState, 1)));
    }
  }

  @Override
  public Q getUserComments(Long userId) {
    return Q.ok().put("data", this.baseMapper.selectList(new QueryWrapper<UComments>().lambda().eq(UComments::getUserId, userId).eq(UComments::getState, 1)));
  }

  @Override
  public Q getAllComments(Integer pageNumber, Integer pageSize, Integer condition) {
    if (condition == 0) {
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.selectList(new QueryWrapper<UComments>().orderByAsc("product_id")));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), new QueryWrapper<UComments>().orderByAsc("product_id")));
      }
    } else {
      QueryWrapper<UComments> conditionQueryWrapper = new QueryWrapper<UComments>()
          .and(qw -> qw.like("id", condition).or()
              .like("user_id", condition).or()
              .like("product_id", condition).or()
              .like("product_type", condition).or()
              .like("state", condition))
          .orderByAsc("product_id");
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.selectList(conditionQueryWrapper));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), conditionQueryWrapper));
      }
    }
  }

  public Boolean isComments(Long userId, Long productId) {
    return this.baseMapper.selectOne(new QueryWrapper<UComments>().lambda().eq(UComments::getUserId, userId).eq(UComments::getProductId, productId)) != null;
  }
}
