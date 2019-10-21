package org.jeecg.modules.manage.entity;

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
 * @Date:   2019-10-19
 * @Version: V1.0
 */
@Data
@TableName("exam_list")
public class ExamList implements Serializable {
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
	/**考试名称*/
	@Excel(name = "考试名称", width = 15)
	private String examName;
	/**考试开始时间*/
	@Excel(name = "考试开始时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date examStartTime;
	/**考试结束时间*/
	@Excel(name = "考试结束时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date examEndTime;
	/**题库名称*/
	@Excel(name = "题库名称", width = 15)
	private String bankId;
	/**单选题数量*/
	@Excel(name = "单选题数量", width = 15)
	private Integer singleChoiceNum;
	/**单选题分数*/
	@Excel(name = "单选题分数", width = 15)
	private Integer singleChoiceMark;
	/**多选题数量*/
	@Excel(name = "多选题数量", width = 15)
	private Integer multipleChoiceNum;
	/**多选题分数*/
	@Excel(name = "多选题分数", width = 15)
	private Integer multipleChoiceMark;
	/**判断题数量*/
	@Excel(name = "判断题数量", width = 15)
	private Integer judgmentNum;
	/**判断题分数*/
	@Excel(name = "判断题分数", width = 15)
	private Integer judgmentMark;
	/**及格分数*/
	@Excel(name = "及格分数", width = 15)
	private Double examPassMark;
	/**总分*/
	@Excel(name = "总分", width = 15)
	private Integer examTotalMark;
	/**考试说明*/
	@Excel(name = "考试说明", width = 15)
	private String examExplain;
	/**状态*/
	@Excel(name = "状态", width = 15)
	private String status;
}
