package org.jeecg.modules.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.manage.entity.ExamList;
import org.jeecg.modules.manage.entity.ExamUserPaper;

/**
 * @Description: 学生-试卷关系
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
public interface ExamUserPaperMapper extends BaseMapper<ExamUserPaper> {
    String TABLE = "exam_user_paper";

    @Select("select * from "+TABLE+" where user_id = #{userId} and paper_id =#{paperId}")
    ExamUserPaper getExamUserPaper(@Param("userId")String userId,@Param("paperId")String paperId);

}
