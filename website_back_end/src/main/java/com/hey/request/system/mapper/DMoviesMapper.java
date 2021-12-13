package com.hey.request.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hey.request.system.entity.DMovies;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Request404
 * @since 2021-11-15
 */
public interface DMoviesMapper extends BaseMapper<DMovies> {

  List<Map<String, Object>> dMoviesAssociationScore(@Param("ew") Wrapper<DMovies> wrapper);

  Page<Map<String, Object>> dMoviesAssociationScore(Page<DMovies> page, @Param("ew") Wrapper<DMovies> wrapper);
}
