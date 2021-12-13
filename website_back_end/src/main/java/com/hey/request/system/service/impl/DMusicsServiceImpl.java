package com.hey.request.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hey.request.security.entity.SysUserDetails;
import com.hey.request.system.entity.DMusics;
import com.hey.request.system.entity.UCollections;
import com.hey.request.system.entity.UScore;
import com.hey.request.system.mapper.DMusicsMapper;
import com.hey.request.system.mapper.UCollectionsMapper;
import com.hey.request.system.service.DMusicsService;
import com.hey.request.system.service.DRecordService;
import com.hey.request.system.service.UScoreService;
import com.hey.request.system.utils.Q;
import com.hey.request.system.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
public class DMusicsServiceImpl extends ServiceImpl<DMusicsMapper, DMusics> implements DMusicsService {

  private final UCollectionsMapper uCollectionsMapper;

  private final UScoreService uScoreService;

  private final DRecordService dRecordService;

  @Override
  @Transactional
  public Integer addMusic(Authentication authentication, DMusics dMusics) {

    String datetime = Utils.GenerateDatetime();
    String date = datetime.substring(0, 10);

    SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
    dMusics.setOperationUser(userDetails.getUsername());
    dMusics.setOperationTime(datetime);

    int dMusicsInsert = this.baseMapper.insert(dMusics);

    UScore uScore = new UScore();
    uScore.setProductId(dMusics.getId());
    uScore.setProductType(Utils.productType(dMusics.getId()));
    uScore.setPostDate(date);

    int dBooksScore = uScoreService.addProductScore(uScore);

    if (dMusicsInsert == 1 && dBooksScore == 1) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  @Transactional
  public Integer removeDMusic(Long id) {
    int scoreRemove = uScoreService.removeProductScore(id);
    int musicRemove = this.baseMapper.deleteById(id);
    if (scoreRemove == 1 && musicRemove == 1) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  public Integer getOffMusic(Long id) {
    DMusics dMusics = this.baseMapper.selectById(id);
    UCollections uCollections = new UCollections();
    uCollections.setProductId(dMusics.getId());

    if (dMusics.getState() == 1) {
      dMusics.setState((short) 0);
      uCollections.setState((short) 0);
    } else {
      dMusics.setState((short) 1);
      uCollections.setState((short) 1);
    }
    uCollectionsMapper.update(uCollections, new UpdateWrapper<UCollections>().lambda().eq(UCollections::getProductId, uCollections.getProductId()));
    return this.baseMapper.updateById(dMusics);
  }

  @Override
  public Integer updateMusic(Authentication authentication, DMusics dMusics) {
    SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
    dMusics.setOperationUser(userDetails.getUsername());
    dMusics.setOperationTime(Utils.GenerateDatetime());
    return this.baseMapper.updateById(dMusics);
  }

  @Override
  public Q getMusicInfo(Long id) {
    return Q.ok().put("data", this.baseMapper.dMusicsAssociationScore(new QueryWrapper<DMusics>().eq("m.id", id).eq("state", 1)));
  }

  @Override
  public Q getAllMusics(Integer pageNumber, Integer pageSize, String condition) {
    if (condition.trim().equals("")) {
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.dMusicsAssociationScore(null));
      } else {
        return Q.ok().put("data", this.baseMapper.dMusicsAssociationScore(new Page<>(pageNumber, pageSize), null));
      }
    } else {
      QueryWrapper<DMusics> conditionQueryWrapper = new QueryWrapper<DMusics>()
          .like("m.name", condition).or()
          .like("m.album", condition).or()
          .like("m.singer", condition).or()
          .like("m.write_words", condition).or()
          .like("m.compose", condition).or()
          .like("m.type", condition).or()
          .like("m.country", condition).or()
          .like("m.release_year", condition).or()
          .like("m.album_avatar_url", condition).or()
          .like("m.singer_avatar_url", condition).or()
          .like("m.operation_time", condition).or()
          .like("m.operation_user", condition);
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.dMusicsAssociationScore(conditionQueryWrapper));
      } else {
        return Q.ok().put("data", this.baseMapper.dMusicsAssociationScore(new Page<>(pageNumber, pageSize), conditionQueryWrapper));
      }
    }
  }

  @Override
  public Q clientGetAllMusics(Integer pageNumber, Integer pageSize, String condition) {
    if (condition.trim().equals("")) {
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.dMusicsAssociationScore(new QueryWrapper<DMusics>().lambda().eq(DMusics::getState, 1)));
      } else {
        return Q.ok().put("data", this.baseMapper.dMusicsAssociationScore(new Page<>(pageNumber, pageSize), new QueryWrapper<DMusics>().lambda().eq(DMusics::getState, 1)));
      }
    } else {
      QueryWrapper<DMusics> conditionQueryWrapper = new QueryWrapper<DMusics>()
          .eq("state", 1)
          .and(qw -> qw.like("name", condition).or()
              .like("m.album", condition).or()
              .like("m.singer", condition).or()
              .like("m.write_words", condition).or()
              .like("m.compose", condition).or()
              .like("m.lyric", condition).or()
              .like("m.type", condition).or()
              .like("m.country", condition).or()
              .like("m.release_year", condition));
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.dMusicsAssociationScore(conditionQueryWrapper));
      } else {
        return Q.ok().put("data", this.baseMapper.dMusicsAssociationScore(new Page<>(pageNumber, pageSize), conditionQueryWrapper));
      }
    }
  }

  @Override
  public Q getRecommendsMusics(Integer pageNumber, Integer pageSize, Long userId) {
    return null;
  }

  @Override
  public Q getWebsiteIndexMusics(Integer needCover) {
    return dRecordService.getRecommendRecordByProductType((short) 3, needCover);
  }
}
