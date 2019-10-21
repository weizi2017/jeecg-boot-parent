package org.jeecg.modules.manage.job;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.manage.entity.ExamList;
import org.jeecg.modules.manage.mapper.ExamListMapper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class ExamStatusJob implements Job {

    @Autowired
    private ExamListMapper examListMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<ExamList> examLists = examListMapper.selectAllExamList();
        long totalMilliSeconds = System.currentTimeMillis();
        log.info("更新考试状态,当前时间:"+totalMilliSeconds);
        //根据时间判断考试状态
        for(ExamList examList:examLists){
            if(!examList.getStatus().equals("2")) {
                long StartTime = examList.getExamStartTime().getTime();
                long endTime = examList.getExamEndTime().getTime();
                if ("0".equals(examList.getStatus())) {
                    if (totalMilliSeconds >= StartTime) {
                        examListMapper.updateExamListStatus(examList.getId(), "1");
                    }
                } else if ("1".equals(examList.getStatus())) {
                    if (totalMilliSeconds >= endTime) {
                        examListMapper.updateExamListStatus(examList.getId(), "2");
                    }
                }
            }
        }
    }
}
