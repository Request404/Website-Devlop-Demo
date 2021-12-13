package com.hey.request.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hey.request.security.entity.SysUserDetails;
import com.hey.request.system.entity.*;
import com.hey.request.system.mapper.*;
import com.hey.request.system.service.DRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hey.request.system.utils.Q;
import com.hey.request.system.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Request404
 * @since 2021-11-28
 */
@Service
@CacheConfig(cacheManager = "dayCacheManager")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DRecordServiceImpl extends ServiceImpl<DRecordMapper, DRecord> implements DRecordService {

  private final DBooksMapper dBooksMapper;

  private final DMoviesMapper dMoviesMapper;

  private final DMusicsMapper dMusicsMapper;

  private final UScoreMapper uScoreMapper;

  @Override
  public Integer addRecommendRecord(Authentication authentication, DRecord dRecord) {
    if (this.baseMapper.selectOne(new QueryWrapper<DRecord>().lambda().eq(DRecord::getProductId, dRecord.getProductId())) == null) {
      SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
      dRecord.setProductType(Utils.productType(dRecord.getProductId()));
      dRecord.setOperationUser(userDetails.getUsername());
      dRecord.setOperationTime(Utils.GenerateDatetime());
      return this.baseMapper.insert(dRecord);
    } else {
      return -1;
    }
  }

  @Override
  public Integer removeRecommendRecord(Long dRecordId) {
    return this.baseMapper.deleteById(dRecordId);
  }

  @Override
  public Integer updateRecommendRecord(Authentication authentication, DRecord dRecord) {
    SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
    dRecord.setProductType(Utils.productType(dRecord.getProductId()));
    dRecord.setOperationUser(userDetails.getUsername());
    dRecord.setOperationTime(Utils.GenerateDatetime());
    return this.baseMapper.updateById(dRecord);
  }

  @Override
  public Integer getOffRecommendRecord(Long recordId) {
    DRecord dRecord = this.baseMapper.selectById(recordId);
    System.out.println(dRecord + "??????????????????????");
    if (dRecord.getState() == 1) {
      dRecord.setState((short) 0);
    } else {
      dRecord.setState((short) 1);
    }
    return this.baseMapper.updateById(dRecord);
  }

  @Override
  public Q getRecommendRecord(Integer pageNumber, Integer pageSize, Integer condition) {
    if (condition == 0) {
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.selectList(null));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), null));
      }
    } else {
      QueryWrapper<DRecord> conditionQueryWrapper = new QueryWrapper<DRecord>()
          .like("id", condition).or()
          .like("product_id", condition).or()
          .like("product_type", condition).or()
          .like("state", condition);
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.selectList(conditionQueryWrapper));
      } else {
        return Q.ok().put("data", this.baseMapper.selectPage(new Page<>(pageNumber, pageSize), conditionQueryWrapper));
      }
    }
  }

  @Override
  @Cacheable(value = "RecommendRecord", key = "#productType+#needCover*100")
  public Q getRecommendRecordByProductType(Short productType, Integer needCover) {
    List<DRecord> RecommendRecord = new ArrayList<>();
    List<DRecord> DRecords;
    if(needCover == 0){
      DRecords = this.baseMapper.selectList(new QueryWrapper<DRecord>().lambda().eq(DRecord::getProductType, productType).eq(DRecord::getState, 1));
    }else{
      DRecords = this.baseMapper.selectList(new QueryWrapper<DRecord>().lambda().eq(DRecord::getProductType, productType).eq(DRecord::getState, 1).ne(DRecord::getProductCoverUrl, "").isNotNull(DRecord::getProductCoverUrl));
    }
    if (1 == productType) {
      for (DRecord bookDRecords : DRecords) {
        DBooks dBooks = dBooksMapper.selectOne(new QueryWrapper<DBooks>().lambda().eq(DBooks::getId, bookDRecords.getProductId()));
        UScore uScore = uScoreMapper.selectOne(new QueryWrapper<UScore>().lambda().eq(UScore::getProductId, bookDRecords.getProductId()));
        bookDRecords.setProductData(dBooks);
        bookDRecords.setScore(uScore);
        RecommendRecord.add(bookDRecords);
      }
    } else if (2 == productType) {
      for (DRecord moviesDRecords : DRecords) {
        DMovies dMovies = dMoviesMapper.selectOne(new QueryWrapper<DMovies>().lambda().eq(DMovies::getId, moviesDRecords.getProductId()));
        UScore uScore = uScoreMapper.selectOne(new QueryWrapper<UScore>().lambda().eq(UScore::getProductId, moviesDRecords.getProductId()));
        moviesDRecords.setProductData(dMovies);
        moviesDRecords.setScore(uScore);
        RecommendRecord.add(moviesDRecords);
      }
    } else if (3 == productType) {
      for (DRecord musicsDRecords : DRecords) {
        DMusics dMusics = dMusicsMapper.selectOne(new QueryWrapper<DMusics>().lambda().eq(DMusics::getId, musicsDRecords.getProductId()));
        UScore uScore = uScoreMapper.selectOne(new QueryWrapper<UScore>().lambda().eq(UScore::getProductId, musicsDRecords.getProductId()));
        musicsDRecords.setProductData(dMusics);
        musicsDRecords.setScore(uScore);
        RecommendRecord.add(musicsDRecords);
      }
    } else {
      return Q.error(500, "未知类型");
    }
    return Q.ok().put("data", RecommendRecord);
  }

  @Override
  @Cacheable(value = "CubeRecommend")
  public Q getIndexCubeRecommend() {
    List<DRecord> RecommendRecord = new ArrayList<>();
    for (int i = 1; i <= 4; i++) {
      DRecord dRecord;
      if (i == 4) {
        List<DRecord> dRecord_list = this.baseMapper.selectList(new QueryWrapper<DRecord>().lambda().eq(DRecord::getProductType, 2).ne(DRecord::getProductCoverUrl, "").eq(DRecord::getState, 1).isNotNull(DRecord::getProductCoverUrl).orderByAsc(DRecord::getOperationTime));
        if (dRecord_list != null && dRecord_list.size() >= 2) {
          dRecord = dRecord_list.get(1);
        } else {
          dRecord = null;
        }
      } else {
        List<DRecord> dRecord_list = this.baseMapper.selectList(new QueryWrapper<DRecord>().lambda().eq(DRecord::getProductType, i).ne(DRecord::getProductCoverUrl, "").eq(DRecord::getState, 1).isNotNull(DRecord::getProductCoverUrl).orderByAsc(DRecord::getOperationTime));
        if (dRecord_list != null && dRecord_list.size() >= 1) {
          dRecord = dRecord_list.get(0);
        } else {
          dRecord = null;
        }
      }
      if (dRecord != null && 1 == dRecord.getProductType()) {
        dRecord.setProductData(dBooksMapper.selectOne(new QueryWrapper<DBooks>().lambda().eq(DBooks::getId, dRecord.getProductId())));
        dRecord.setScore(uScoreMapper.selectOne(new QueryWrapper<UScore>().lambda().eq(UScore::getProductId, dRecord.getProductId())));
        RecommendRecord.add(dRecord);
      } else if (dRecord != null && 2 == dRecord.getProductType()) {
        dRecord.setProductData(dMoviesMapper.selectOne(new QueryWrapper<DMovies>().lambda().eq(DMovies::getId, dRecord.getProductId())));
        dRecord.setScore(uScoreMapper.selectOne(new QueryWrapper<UScore>().lambda().eq(UScore::getProductId, dRecord.getProductId())));
        RecommendRecord.add(dRecord);
      } else if (dRecord != null && 3 == dRecord.getProductType()) {
        dRecord.setProductData(dMusicsMapper.selectOne(new QueryWrapper<DMusics>().lambda().eq(DMusics::getId, dRecord.getProductId())));
        dRecord.setScore(uScoreMapper.selectOne(new QueryWrapper<UScore>().lambda().eq(UScore::getProductId, dRecord.getProductId())));
        RecommendRecord.add(dRecord);
      }
    }
    return Q.ok().put("data", RecommendRecord);
  }
}
