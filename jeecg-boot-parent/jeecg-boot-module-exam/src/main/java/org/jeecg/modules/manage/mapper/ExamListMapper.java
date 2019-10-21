package org.jeecg.modules.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.manage.entity.ExamList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.manage.entity.ExamList;

/**
 * @Description: 考试列表
 * @Author: jeecg-boot
 * @Date:   2019-10-19
 * @Version: V1.0
 */
public interface ExamListMapper extends BaseMapper<ExamList> {

    String TABLE = "exam_list";

    @Update("update "+TABLE+" set status = #{Status} where id = #{id}")
    int updateExamListStatus(@Param("id")String id, @Param("Status")String Status);

    @Select("select * from " +TABLE)
    List<ExamList> selectAllExamList();


}
