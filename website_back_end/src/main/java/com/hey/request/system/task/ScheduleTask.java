package com.hey.request.system.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hey.request.system.entity.DRecord;
import com.hey.request.system.entity.UScore;
import com.hey.request.system.mapper.DRecordMapper;
import com.hey.request.system.mapper.UScoreMapper;
import com.hey.request.system.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScheduleTask {

  private final UScoreMapper uScoreMapper;

  private final DRecordMapper dRecordMapper;

  @Scheduled(cron = "0 55 23 * * ?")
  public void UScoreVisitUpdatTask() {
    List<UScore> uScores = uScoreMapper.selectList(null);
    for (UScore uScore : uScores) {
      uScore.setYesterdayVisit(uScore.getTodayVisit());
      List<Integer> visits = Utils.stringToNumberList(uScore.getRecordDateVisit());
      uScore.setSevenVisit(uScore.getSevenVisit() - visits.get(0) + uScore.getTodayVisit());
      visits.remove(0);
      visits.add(uScore.getTodayVisit());
      uScore.setTodayVisit(0);
      uScore.setRecordDateVisit(this.newRecordDateVisit(visits));
      uScoreMapper.updateById(uScore);
    }
  }

  @Scheduled(cron = "0 55 23 * * ?")
  @Transactional
  public void IndexRecommendUpdateTask() {
    dRecordMapper.delete(new QueryWrapper<DRecord>().eq("product_cover_url", "").isNull("product_cover_url").between("operation_time", Utils.GeneratePassDatetime(2),  Utils.GeneratePassDatetime(1)));
    for (int i = 1; i < 4; i++) {
      QueryWrapper<UScore> conditionQueryWrapper = new QueryWrapper<UScore>()
          .eq("product_type", i)
          .between("post_date", Utils.GeneratePassDatetime(7), Utils.GenerateDatetime())
          .orderByDesc("statistic_point")
          .last("limit 10");
      List<UScore> uScores = uScoreMapper.selectList(conditionQueryWrapper);
      if (uScores.size() < 10) {
        QueryWrapper<UScore> globalConditionQueryWrapper = new QueryWrapper<UScore>()
            .eq("product_type", i)
            .orderByDesc("statistic_point")
            .last("limit " + (10 - uScores.size()));
        List<UScore> globalUScores = uScoreMapper.selectList(globalConditionQueryWrapper);
        uScores.addAll(globalUScores);
        this.recommendUpdate(uScores);
      } else {
        this.recommendUpdate(uScores);
      }
    }
  }


  private String newRecordDateVisit(List<Integer> visits) {
    return StringUtils.strip(String.valueOf(visits), "[]");
  }

  private void recommendUpdate(List<UScore> uScores) {
    for (UScore uScore : uScores) {
      if(dRecordMapper.selectOne(new QueryWrapper<DRecord>().lambda().eq(DRecord::getProductId, uScore.getProductId()))==null){
        DRecord dRecord = new DRecord();
        dRecord.setProductId(uScore.getProductId());
        dRecord.setProductType(uScore.getProductType());
        dRecord.setOperationTime(Utils.GenerateDatetime());
        dRecord.setOperationUser("Scheduled");
        dRecordMapper.insert(dRecord);
      }
    }
  }
}
