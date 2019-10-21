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
 * @Description: 试卷
 * @Author: jeecg-boot
 * @Date:   2019-10-19
 * @Version: V1.0
 */
@Data
@TableName("exam_paper")
public class ExamPaper implements Serializable {
    private static final long serialVersionUID = 1L;

	/**题目类型*/
	@Excel(name = "题目类型", width = 15)
	private java.lang.String quesType;
	/**题库id*/
	@Excel(name = "题库id", width = 15)
	private java.lang.String examBankId;
	/**题目内容*/
	@Excel(name = "题目内容", width = 15)
	private java.lang.String quesContent;
	/**题目图片1*/
	@Excel(name = "题目图片1", width = 15)
	private java.lang.String quesImg1;
	/**题目图片2*/
	@Excel(name = "题目图片2", width = 15)
	private java.lang.String quesImg2;
	/**题目图片3*/
	@Excel(name = "题目图片3", width = 15)
	private java.lang.String quesImg3;
	/**题目图片4*/
	@Excel(name = "题目图片4", width = 15)
	private java.lang.String quesImg4;
	/**文字选项1*/
	@Excel(name = "文字选项1", width = 15)
	private java.lang.String quesCheck1;
	/**文字选项2*/
	@Excel(name = "文字选项2", width = 15)
	private java.lang.String quesCheck2;
	/**文字选项3*/
	@Excel(name = "文字选项3", width = 15)
	private java.lang.String quesCheck3;
	/**文字选项4*/
	@Excel(name = "文字选项4", width = 15)
	private java.lang.String quesCheck4;
	/**图片选项1*/
	@Excel(name = "图片选项1", width = 15)
	private java.lang.String quesCheckImg1;
	/**图片选项2*/
	@Excel(name = "图片选项2", width = 15)
	private java.lang.String quesCheckImg2;
	/**图片选项3*/
	@Excel(name = "图片选项3", width = 15)
	private java.lang.String quesCheckImg3;
	/**图片选项4*/
	@Excel(name = "图片选项4", width = 15)
	private java.lang.String quesCheckImg4;
	/**答案*/
	@Excel(name = "答案", width = 15)
	private java.lang.String quesAnswer;
	/**答案解析*/
	@Excel(name = "答案解析", width = 15)
	private java.lang.String quesKey;
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
	/**试卷id*/
	@Excel(name = "试卷id", width = 15)
	private String examListId;
	/**题目id*/
	@Excel(name = "题目id", width = 15)
	private String examQuesId;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
	private String userId;
	/**本题答案*/
	@Excel(name = "本题答案", width = 15)
	private String examResult;
	/**本题得分*/
	@Excel(name = "本题得分", width = 15)
	private String examResultMark;
}
