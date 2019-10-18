package org.jeecg.modules.demo.manage.entity;

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
 * @Description: 考试列表
 * @Author: jeecg-boot
 * @Date:   2019-10-18
 * @Version: V1.0
 */
@Data
@TableName("exam_list")
public class ExamList implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	private java.lang.String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
	private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
	private java.lang.String sysOrgCode;
	/**考试名称*/
	@Excel(name = "考试名称", width = 15)
	private java.lang.String examName;
	/**考试开始时间*/
	@Excel(name = "考试开始时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date examStartTime;
	/**考试结束时间*/
	@Excel(name = "考试结束时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date examEndTime;
	/**题库id*/
	@Excel(name = "题库id", width = 15)
	private java.lang.String bankId;
	/**题库名称*/
	@Excel(name = "题库名称", width = 15)
	private java.lang.String bankName;
	/**单选题数量*/
	@Excel(name = "单选题数量", width = 15)
	private java.lang.Integer singleChoiceNum;
	/**单选题分数*/
	@Excel(name = "单选题分数", width = 15)
	private java.lang.Integer singleChoiceMark;
	/**多选题数量*/
	@Excel(name = "多选题数量", width = 15)
	private java.lang.Integer multipleChoiceNum;
	/**多选题分数*/
	@Excel(name = "多选题分数", width = 15)
	private java.lang.Integer multipleChoiceMark;
	/**判断题数量*/
	@Excel(name = "判断题数量", width = 15)
	private java.lang.Integer judgmentNum;
	/**判断题分数*/
	@Excel(name = "判断题分数", width = 15)
	private java.lang.Integer judgmentMark;
	/**及格分数*/
	@Excel(name = "及格分数", width = 15)
	private java.lang.Double examPassMark;
	/**总分*/
	@Excel(name = "总分", width = 15)
	private java.lang.Integer examTotalMark;
	/**考试说明*/
	@Excel(name = "考试说明", width = 15)
	private java.lang.String examExplain;
}
