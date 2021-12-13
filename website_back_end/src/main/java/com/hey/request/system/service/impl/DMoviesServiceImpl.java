package com.hey.request.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hey.request.security.entity.SysUserDetails;
import com.hey.request.system.entity.DMovies;
import com.hey.request.system.entity.UCollections;
import com.hey.request.system.entity.UScore;
import com.hey.request.system.mapper.DMoviesMapper;
import com.hey.request.system.mapper.UCollectionsMapper;
import com.hey.request.system.service.DMoviesService;
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
public class DMoviesServiceImpl extends ServiceImpl<DMoviesMapper, DMovies> implements DMoviesService {

  private final UCollectionsMapper uCollectionsMapper;

  private final UScoreService uScoreService;

  private final DRecordService dRecordService;

  @Override
  @Transactional
  public Integer addMovie(Authentication authentication, DMovies dMovies) {
    String datetime = Utils.GenerateDatetime();
    String date = datetime.substring(0, 10);

    SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
    dMovies.setOperationUser(userDetails.getUsername());
    dMovies.setOperationTime(datetime);

    int dMoviesInsert = this.baseMapper.insert(dMovies);

    UScore uScore = new UScore();
    uScore.setProductId(dMovies.getId());
    uScore.setProductType(Utils.productType(dMovies.getId()));
    uScore.setPostDate(date);

    int dBooksScore = uScoreService.addProductScore(uScore);

    if (dMoviesInsert == 1 && dBooksScore == 1) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  @Transactional
  public Integer removeDMovie(Long id) {
    int scoreRemove = uScoreService.removeProductScore(id);
    int bookRemove = this.baseMapper.deleteById(id);
    if (scoreRemove == 1 && bookRemove == 1) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  public Integer getOffMovie(Long id) {
    DMovies dMovies = this.baseMapper.selectById(id);
    UCollections uCollections = new UCollections();
    uCollections.setProductId(dMovies.getId());

    if(dMovies.getState() == 1){
      dMovies.setState((short) 0);
      uCollections.setState((short) 0);
    }else{
      dMovies.setState((short) 1);
      uCollections.setState((short) 1);
    }
    uCollectionsMapper.update(uCollections, new UpdateWrapper<UCollections>().lambda().eq(UCollections::getProductId, uCollections.getProductId()));
    return this.baseMapper.updateById(dMovies);
  }

  @Override
  public Integer updateMovie(Authentication authentication, DMovies dMovies) {
    SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
    dMovies.setOperationUser(userDetails.getUsername());
    dMovies.setOperationTime(Utils.GenerateDatetime());
    return this.baseMapper.updateById(dMovies);
  }

  @Override
  public Q getMovieInfo(Long id) {
    return Q.ok().put("data", this.baseMapper.dMoviesAssociationScore(new QueryWrapper<DMovies>().eq("m.id", id).eq("state", 1)));
  }

  @Override
  public Q getAllMovies(Integer pageNumber, Integer pageSize, String condition) {
    if (condition.trim().equals("")) {
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.dMoviesAssociationScore(null));
      } else {
        return Q.ok().put("data", this.baseMapper.dMoviesAssociationScore(new Page<>(pageNumber, pageSize), null));
      }
    } else {
      QueryWrapper<DMovies> conditionQueryWrapper = new QueryWrapper<DMovies>()
          .like("m.name", condition).or()
          .like("m.type", condition).or()
          .like("m.country", condition).or()
          .like("m.release_date", condition).or()
          .like("m.director", condition).or()
          .like("m.writer", condition).or()
          .like("m.actors", condition).or()
          .like("m.alternate_name", condition).or()
          .like("m.cover_url", condition).or()
          .like("m.operation_time", condition).or()
          .like("m.operation_user", condition);
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.dMoviesAssociationScore(conditionQueryWrapper));
      } else {
        return Q.ok().put("data", this.baseMapper.dMoviesAssociationScore(new Page<>(pageNumber, pageSize), conditionQueryWrapper));
      }
    }
  }

  @Override
  public Q clientGetAllMovies(Integer pageNumber, Integer pageSize, String condition) {
    if (condition.trim().equals("")) {
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.dMoviesAssociationScore(new QueryWrapper<DMovies>().lambda().eq(DMovies::getState, 1)));
      } else {
        return Q.ok().put("data", this.baseMapper.dMoviesAssociationScore(new Page<>(pageNumber, pageSize), new QueryWrapper<DMovies>().lambda().eq(DMovies::getState, 1)));
      }
    } else {
      QueryWrapper<DMovies> conditionQueryWrapper = new QueryWrapper<DMovies>()
          .eq("state", 1)
          .and(qw -> qw.like("name", condition).or()
              .like("m.type", condition).or()
              .like("m.country", condition).or()
              .like("m.release_date", condition).or()
              .like("m.director", condition).or()
              .like("m.writer", condition).or()
              .like("m.actors", condition).or()
              .like("m.alternate_name", condition));
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.dMoviesAssociationScore(conditionQueryWrapper));
      } else {
        return Q.ok().put("data", this.baseMapper.dMoviesAssociationScore(new Page<>(pageNumber, pageSize), conditionQueryWrapper));
      }
    }
  }

  @Override
  public Q getRecommendsMovies(Integer pageNumber, Integer pageSize, Long userId) {
    return null;
  }

  @Override
  public Q getWebsiteIndexMovies(Integer needCover) {
    return dRecordService.getRecommendRecordByProductType((short) 2, needCover);
  }
}
