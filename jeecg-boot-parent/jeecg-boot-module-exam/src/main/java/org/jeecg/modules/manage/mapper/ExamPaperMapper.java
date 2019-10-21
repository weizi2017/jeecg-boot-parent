package org.jeecg.modules.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.manage.entity.ExamPaper;
import org.jeecg.modules.manage.entity.ExamUserPaper;

/**
 * @Description: 试卷
 * @Author: jeecg-boot
 * @Date: 2019-10-19
 * @Version: V1.0
 */
public interface ExamPaperMapper extends BaseMapper<ExamPaper> {

    String TABLE = "exam_paper";

    //获取用户试卷
    //@Select("Select * from "+TABLE +" where user_id = #{userId} and exam_list_id =#{examListId}")
    List<ExamPaper> getExamPaperList(@Param("userId") String userId, @Param("examListId") String examListId);

    @Update("update " + TABLE + " set exam_result = #{examResult},exam_result_mark =#{examResultMark} where id = #{id}")
    int updateExamAnswer(@Param("id") String id, @Param("examResult") String examResult, @Param("examResultMark") String examResultMark);


}
