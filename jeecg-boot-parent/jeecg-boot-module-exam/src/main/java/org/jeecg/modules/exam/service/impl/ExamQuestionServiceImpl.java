package org.jeecg.modules.exam.service.impl;


import org.jeecg.modules.exam.entity.ExamQuestion;
import org.jeecg.modules.exam.mapper.ExamQuestionMapper;
import org.jeecg.modules.exam.service.IExamQuestionService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 存储题目
 * @Author: jeecg-boot
 * @Date:   2019-10-18
 * @Version: V1.0
 */
@Service
public class ExamQuestionServiceImpl extends ServiceImpl<ExamQuestionMapper, ExamQuestion> implements IExamQuestionService {

}
