package org.jeecg.modules.exam.mapper;


import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.exam.entity.ExamQuestion;

import java.util.List;

/**
 * @Description: 存储题目
 * @Author: jeecg-boot
 * @Date:   2019-10-18
 * @Version: V1.0
 */
public interface ExamQuestionMapper extends BaseMapper<ExamQuestion> {

    String TABLE = "exam_question";

    @Select("select count(*) from "+TABLE +" where ques_type=#{quesType} and exam_bank_id=#{examBankId}")
    int getQuesNumByBank(@Param("quesType")String quesType,@Param("examBankId")String examBankId);

    @Select("select * from " +TABLE+" where exam_bank_id=#{examBankId} and ques_type=#{quesType}")
    List<ExamQuestion> getQueListByBank(@Param("examBankId")String examBankId,@Param("quesType")String quesType);


}
