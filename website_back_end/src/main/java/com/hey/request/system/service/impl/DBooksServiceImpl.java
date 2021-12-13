package com.hey.request.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hey.request.security.entity.SysUserDetails;
import com.hey.request.system.entity.DBooks;
import com.hey.request.system.entity.UCollections;
import com.hey.request.system.entity.UComments;
import com.hey.request.system.entity.UScore;
import com.hey.request.system.mapper.DBooksMapper;
import com.hey.request.system.mapper.UCollectionsMapper;
import com.hey.request.system.service.*;
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
public class DBooksServiceImpl extends ServiceImpl<DBooksMapper, DBooks> implements DBooksService {

  private final UCollectionsMapper uCollectionsMapper;

  private final UScoreService uScoreService;

  private final DRecordService dRecordService;

  @Override
  @Transactional
  public Integer addBook(Authentication authentication, DBooks dBooks) {
    boolean isReqeat = this.baseMapper.selectOne(new QueryWrapper<DBooks>().lambda().eq(DBooks::getIsbn, dBooks.getIsbn())) != null;
    if (isReqeat) {
      return -1;
    } else {
      String datetime = Utils.GenerateDatetime();
      String date = datetime.substring(0, 10);

      SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
      dBooks.setOperationUser(userDetails.getUsername());
      dBooks.setOperationTime(datetime);

      int dBooksInsert = this.baseMapper.insert(dBooks);

      UScore uScore = new UScore();
      uScore.setProductId(dBooks.getId());
      uScore.setProductType(Utils.productType(dBooks.getId()));
      uScore.setPostDate(date);

      int dBooksScore = uScoreService.addProductScore(uScore);

      if (dBooksInsert == 1 && dBooksScore == 1) {
        return 1;
      } else {
        return 0;
      }
    }
  }

  @Override
  @Transactional
  public Integer removeBook(Long id) {
    int scoreRemove = uScoreService.removeProductScore(id);
    int bookRemove = this.baseMapper.deleteById(id);
    if (scoreRemove == 1 && bookRemove == 1) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  @Transactional
  public Integer getOffBook(Long id) {
    DBooks dBooks = this.baseMapper.selectById(id);
    UCollections uCollections = new UCollections();
    uCollections.setProductId(dBooks.getId());
    if(dBooks.getState() == 1){
      dBooks.setState((short) 0);
      uCollections.setState((short) 0);
    }else{
      dBooks.setState((short) 1);
      uCollections.setState((short) 1);
    }
    uCollectionsMapper.update(uCollections, new UpdateWrapper<UCollections>().lambda().eq(UCollections::getProductId, uCollections.getProductId()));
    return this.baseMapper.updateById(dBooks);
  }

  @Override
  public Integer updateBook(Authentication authentication, DBooks dBooks) {
    SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
    dBooks.setOperationUser(userDetails.getUsername());
    dBooks.setOperationTime(Utils.GenerateDatetime());
    return this.baseMapper.updateById(dBooks);
  }

  @Override
  public Q getBookInfo(Long id) {
    return Q.ok().put("data", this.baseMapper.dBooksAssociationScore(new QueryWrapper<DBooks>().eq("b.id", id).eq("state", 1)));
  }

  @Override
  public Q getAllBooks(Integer pageNumber, Integer pageSize, String condition) {
    if (condition.trim().equals("")) {
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.dBooksAssociationScore(null));
      } else {
        return Q.ok().put("data", this.baseMapper.dBooksAssociationScore(new Page<>(pageNumber, pageSize), null));
      }
    } else {
      QueryWrapper<DBooks> conditionQueryWrapper = new QueryWrapper<DBooks>()
          .like("b.name", condition).or()
          .like("b.tages", condition).or()
          .like("b.author", condition).or()
          .like("b.country", condition).or()
          .like("b.release_date", condition).or()
          .like("b.cover_url", condition).or()
          .like("b.publisher", condition).or()
          .like("b.isbn", condition).or()
          .like("b.price_source", condition).or()
          .like("b.operation_time", condition).or()
          .like("b.operation_user", condition);
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.dBooksAssociationScore(conditionQueryWrapper));
      } else {
        return Q.ok().put("data", this.baseMapper.dBooksAssociationScore(new Page<>(pageNumber, pageSize), conditionQueryWrapper));
      }
    }
  }

  @Override
  public Q clientGetAllBooks(Integer pageNumber, Integer pageSize, String condition) {
    if (condition.trim().equals("")) {
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.dBooksAssociationScore(new QueryWrapper<DBooks>().lambda().eq(DBooks::getState, 1)));
      } else {
        return Q.ok().put("data", this.baseMapper.dBooksAssociationScore(new Page<>(pageNumber, pageSize), new QueryWrapper<DBooks>().lambda().eq(DBooks::getState, 1)));
      }
    } else {
      QueryWrapper<DBooks> conditionQueryWrapper = new QueryWrapper<DBooks>()
          .eq("state", 1)
          .and(qw -> qw.like("name", condition).or()
              .like("b.title", condition).or()
              .like("b.tages", condition).or()
              .like("b.author", condition).or()
              .like("b.country", condition).or()
              .like("b.release_date", condition).or()
              .like("b.cover_url", condition).or()
              .like("b.publisher", condition).or()
              .like("b.isbn", condition).or()
              .like("b.price_source", condition));
      ;
      if (pageNumber == 0 && pageSize == 0) {
        return Q.ok().put("data", this.baseMapper.dBooksAssociationScore(conditionQueryWrapper));
      } else {
        return Q.ok().put("data", this.baseMapper.dBooksAssociationScore(new Page<>(pageNumber, pageSize), conditionQueryWrapper));
      }
    }
  }

  @Override
  public Q getRecommendsBooks(Integer pageNumber, Integer pageSize, Long userId) {
    return null;
  }

  @Override
  public Q getWebsiteIndexBooks(Integer needCover) {
    return dRecordService.getRecommendRecordByProductType((short) 1, needCover);
  }
}
