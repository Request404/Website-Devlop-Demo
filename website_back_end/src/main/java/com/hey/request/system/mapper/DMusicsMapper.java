package com.hey.request.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hey.request.system.entity.DMusics;
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
public interface DMusicsMapper extends BaseMapper<DMusics> {

  List<Map<String, Object>> dMusicsAssociationScore(@Param("ew") Wrapper<DMusics> wrapper);

  Page<Map<String, Object>> dMusicsAssociationScore(Page<DMusics> page, @Param("ew") Wrapper<DMusics> wrapper);
}
