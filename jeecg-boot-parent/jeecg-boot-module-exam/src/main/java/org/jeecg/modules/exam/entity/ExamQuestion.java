package org.jeecg.modules.exam.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: 存储题目
 * @Author: jeecg-boot
 * @Date:   2019-10-19
 * @Version: V1.0
 */
@Data
@TableName("exam_question")
public class ExamQuestion implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	private String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
	private String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
	private String sysOrgCode;
	/**题目类型*/
	@Excel(name = "题目类型", width = 15)
	private String quesType;
	/**题库名称*/
	@Excel(name = "题库名称", width = 15)
	private String examBankId;
	/**题目内容*/
	@Excel(name = "题目内容", width = 15)
	private String quesContent;
	/**题目图片1*/
	@Excel(name = "题目图片1", width = 15)
	private String quesImg1;
	/**题目图片2*/
	@Excel(name = "题目图片2", width = 15)
	private String quesImg2;
	/**题目图片3*/
	@Excel(name = "题目图片3", width = 15)
	private String quesImg3;
	/**题目图片4*/
	@Excel(name = "题目图片4", width = 15)
	private String quesImg4;
	/**文字选项1*/
	@Excel(name = "文字选项1", width = 15)
	private String quesCheck1;
	/**文字选项2*/
	@Excel(name = "文字选项2", width = 15)
	private String quesCheck2;
	/**文字选项3*/
	@Excel(name = "文字选项3", width = 15)
	private String quesCheck3;
	/**文字选项4*/
	@Excel(name = "文字选项4", width = 15)
	private String quesCheck4;
	/**图片选项1*/
	@Excel(name = "图片选项1", width = 15)
	private String quesCheckImg1;
	/**图片选项2*/
	@Excel(name = "图片选项2", width = 15)
	private String quesCheckImg2;
	/**图片选项3*/
	@Excel(name = "图片选项3", width = 15)
	private String quesCheckImg3;
	/**图片选项4*/
	@Excel(name = "图片选项4", width = 15)
	private String quesCheckImg4;
	/**答案*/
	@Excel(name = "答案", width = 15)
	private String quesAnswer;
	/**答案解析*/
	@Excel(name = "答案解析", width = 15)
	private String quesKey;
}
