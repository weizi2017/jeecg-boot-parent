package org.jeecg.modules.manage.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.exam.entity.ExamQuestion;
import org.jeecg.modules.exam.mapper.ExamQuestionMapper;
import org.jeecg.modules.manage.entity.ExamList;
import org.jeecg.modules.manage.entity.ExamPaper;
import org.jeecg.modules.manage.entity.ExamUserPaper;
import org.jeecg.modules.manage.mapper.ExamListMapper;
import org.jeecg.modules.manage.mapper.ExamPaperMapper;
import org.jeecg.modules.manage.mapper.ExamUserPaperMapper;
import org.jeecg.modules.manage.service.IExamListService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.manage.service.IExamListService;
import org.jeecg.modules.manage.utils.RandomUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;

/**
 * @Description: 考试列表
 * @Author: jeecg-boot
 * @Date: 2019-10-19
 * @Version: V1.0
 */
@RestController
@RequestMapping("/manage/examList")
@Slf4j
public class ExamListController {
    @Autowired
    private IExamListService examListService;

    @Autowired
    private ExamQuestionMapper examQuestionMapper;

    @Autowired
    private ExamListMapper examListMapper;

    @Autowired
    private ExamPaperMapper examPaperMapper;

    @Autowired
    private ExamUserPaperMapper examUserPaperMapper;

    /**
     * 分页列表查询
     *
     * @param examList
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<IPage<ExamList>> queryPageList(ExamList examList,
                                                 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                 HttpServletRequest req) {
        Result<IPage<ExamList>> result = new Result<IPage<ExamList>>();
        QueryWrapper<ExamList> queryWrapper = QueryGenerator.initQueryWrapper(examList, req.getParameterMap());
        Page<ExamList> page = new Page<ExamList>(pageNo, pageSize);
        IPage<ExamList> pageList = examListService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    @GetMapping(value = "/queryExamQuestionList")
    public Result<List<ExamPaper>> queryExamQuestionList(ExamList examList, HttpServletRequest req) {
        Result<List<ExamPaper>> result = new Result<List<ExamPaper>>();
        List<ExamQuestion> examQuestionList = new ArrayList<>();
        List<ExamPaper> examPaperList = new ArrayList<>();
        //获取当前用户
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        //判断当前用户是否进行过考试
        ExamUserPaper examUserPaper = examUserPaperMapper.getExamUserPaper(sysUser.getId(), examList.getId());
        //0 正在进行  1 已交卷 null代表未考过，判断以后更改学生-试卷表的状态
        if (examUserPaper == null || examUserPaper.getId() == null) {
            examUserPaper = new ExamUserPaper();
            examUserPaper.setPaperId(examList.getId());
            examUserPaper.setStatus("0");
            examUserPaper.setUserId(sysUser.getId());
            examUserPaperMapper.insert(examUserPaper);
        }
        //获取试卷列表
        examPaperList = examPaperMapper.getExamPaperList(sysUser.getId(), examList.getBankId());
        //用户已考过
        if (examPaperList != null && examPaperList.size() > 0) {
            result.setSuccess(true);
            result.setResult(examPaperList);
            return result;
        }

        List<ExamQuestion> examSingleChoiceList = examQuestionMapper.getQueListByBank(examList.getBankId(), "0");
        List<ExamQuestion> examMultipleChoiceList = examQuestionMapper.getQueListByBank(examList.getBankId(), "1");
        List<ExamQuestion> examJudgmentList = examQuestionMapper.getQueListByBank(examList.getBankId(), "2");

        if (examSingleChoiceList != null && examJudgmentList != null && examMultipleChoiceList != null && examList != null) {

            int SingleChoiceNum = examList.getSingleChoiceNum();
            int MultipleChoiceNum = examList.getMultipleChoiceNum();
            int JudgmentNum = examList.getJudgmentNum();
            if (SingleChoiceNum > examSingleChoiceList.size()
                    || MultipleChoiceNum > examMultipleChoiceList.size()
                    || JudgmentNum > examJudgmentList.size()
                    || (SingleChoiceNum != 0 && examSingleChoiceList.size() == 0)
                    || (MultipleChoiceNum != 0 && examMultipleChoiceList.size() == 0)
                    || (JudgmentNum != 0 && examJudgmentList.size() == 0)) {
                result.setSuccess(false);
                result.setMessage("数据出错");
                result.setResult(null);
            }
            List<Integer> SingleNumList = RandomUtil.randomNum(SingleChoiceNum, examSingleChoiceList.size() - 1);
            List<Integer> MultipleNumList = RandomUtil.randomNum(MultipleChoiceNum, examMultipleChoiceList.size() - 1);
            List<Integer> JudgmentList = RandomUtil.randomNum(JudgmentNum, examJudgmentList.size() - 1);

            for (int i = 0; i < SingleNumList.size(); i++) {
                examQuestionList.add(examSingleChoiceList.get(SingleNumList.get(i)));
            }
            for (int i = 0; i < MultipleNumList.size(); i++) {
                examQuestionList.add(examMultipleChoiceList.get(MultipleNumList.get(i)));
            }
            for (int i = 0; i < JudgmentList.size(); i++) {
                examQuestionList.add(examJudgmentList.get(JudgmentList.get(i)));
            }
            Collections.shuffle(examQuestionList);
            examPaperList.clear();
            for (int i = 0; i < examQuestionList.size(); i++) {
                ExamPaper examPaper = new ExamPaper();
                examPaper.setExamListId(examList.getId());
                examPaper.setExamQuesId(examQuestionList.get(i).getId());
                examPaper.setUserId(sysUser.getId());

                examPaper.setQuesAnswer(examQuestionList.get(i).getQuesAnswer());
                examPaper.setExamBankId(examQuestionList.get(i).getExamBankId());
                examPaper.setQuesKey(examQuestionList.get(i).getQuesKey());
                examPaper.setQuesCheck1(examQuestionList.get(i).getQuesCheck1());
                examPaper.setQuesCheck2(examQuestionList.get(i).getQuesCheck2());
                examPaper.setQuesCheck3(examQuestionList.get(i).getQuesCheck3());
                examPaper.setQuesCheck4(examQuestionList.get(i).getQuesCheck4());
                examPaper.setQuesCheckImg1(examQuestionList.get(i).getQuesCheckImg1());
                examPaper.setQuesCheckImg2(examQuestionList.get(i).getQuesCheckImg2());
                examPaper.setQuesCheckImg3(examQuestionList.get(i).getQuesCheckImg3());
                examPaper.setQuesCheckImg4(examQuestionList.get(i).getQuesCheckImg4());
                examPaper.setQuesImg1(examQuestionList.get(i).getQuesImg1());
                examPaper.setQuesImg2(examQuestionList.get(i).getQuesImg2());
                examPaper.setQuesImg3(examQuestionList.get(i).getQuesImg3());
                examPaper.setQuesImg4(examQuestionList.get(i).getQuesImg4());
                examPaper.setQuesContent(examQuestionList.get(i).getQuesContent());
                examPaper.setQuesType(examQuestionList.get(i).getQuesType());

                examPaper.setSysOrgCode(examQuestionList.get(i).getSysOrgCode());
                examPaper.setCreateBy(examQuestionList.get(i).getCreateBy());
                examPaper.setUpdateBy(examQuestionList.get(i).getUpdateBy());
                examPaper.setCreateTime(examQuestionList.get(i).getCreateTime());
                examPaper.setUpdateTime(examQuestionList.get(i).getUpdateTime());
                examPaperList.add(examPaper);
            }
            result.setSuccess(true);
            result.setResult(examPaperList);
            return result;
        } else {
            result.setSuccess(true);
            result.setMessage("数据库返回出错");
            result.setResult(examPaperList);
            return result;
        }
    }

    /**
     * 保存用户的选择
     *
     * @param examPaper
     * @param req
     * @return
     */
    @GetMapping(value = "/saveExamResult")
    public Result<Boolean> saveExamResult(ExamPaper examPaper, HttpServletRequest req) {
        Result<Boolean> result = new Result<Boolean>();
        ExamQuestion examQuestion = examQuestionMapper.selectById(examPaper.getExamQuesId());
        ExamList examList = examListMapper.selectById(examPaper.getExamListId());
        if (StringUtils.isNotBlank(examPaper.getQuesAnswer()) && examQuestion != null && StringUtils.isNotBlank(examQuestion.getQuesAnswer())) {
            int mark = 0;
            if (examPaper.getQuesAnswer().equals(examQuestion.getQuesAnswer())) {
                if ("1".equals(examQuestion.getQuesType())) {
                    mark = examList.getSingleChoiceMark();
                } else if ("2".equals(equals(examQuestion.getQuesType()))) {
                    mark = examList.getMultipleChoiceMark();
                } else {
                    mark = examList.getJudgmentMark();
                }
            }
            int ret = examPaperMapper.updateExamAnswer(examPaper.getId(), examPaper.getQuesAnswer(), mark + "");
            if (ret != 0) {
                result.setSuccess(true);
                result.setResult(true);
            } else {
                result.setSuccess(false);
                result.setMessage("数据保存失败");
                result.setResult(false);
            }
        } else {
            result.setSuccess(false);
            result.setMessage("数据获取错误");
            result.setResult(false);
        }
        return result;
    }

    @PostMapping(value = "/advancePaper")
    public Result<List<ExamPaper>> advancePaper(ExamList examList) {
        Result<List<ExamPaper>> result = new Result<List<ExamPaper>>();
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<ExamPaper> examPaperList = examPaperMapper.getExamPaperList(sysUser.getId(), examList.getId());
        if (examPaperList != null) {
            result.setResult(examPaperList);
            result.setSuccess(true);
        } else {
            result.setResult(null);
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 添加
     *
     * @param examList
     * @return
     */
    @PostMapping(value = "/add")
    public Result<ExamList> add(@RequestBody ExamList examList) {
        Result<ExamList> result = new Result<ExamList>();
        try {
            long StartTime = examList.getExamStartTime().getTime();
            long endTime = examList.getExamEndTime().getTime();
            if (StartTime > endTime || endTime - StartTime < 1 * 1000 * 60 * 5) {
                result.error500("操作失败,考试开始时间不能晚于结束时间且考试间隔需大于5分钟");
                return result;
            }
            int SingleChoiceNum = examQuestionMapper.getQuesNumByBank("1", examList.getBankId());
            if (SingleChoiceNum == 0 || SingleChoiceNum < examList.getSingleChoiceNum()) {
                result.error500("操作失败,题库中单选题数目不足");
                return result;
            }
            int multipleChoiceNum = examQuestionMapper.getQuesNumByBank("1", examList.getBankId());
            if (multipleChoiceNum == 0 || multipleChoiceNum < examList.getMultipleChoiceNum()) {
                result.error500("操作失败,题库中多选题数目不足");
                return result;
            }
            int judgmentNum = examQuestionMapper.getQuesNumByBank("1", examList.getBankId());
            if (judgmentNum == 0 || judgmentNum < examList.getJudgmentNum()) {
                result.error500("操作失败,题库中判断题数目不足");
                return result;
            }
            examListService.save(examList);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑
     *
     * @param examList
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<ExamList> edit(@RequestBody ExamList examList) {
        Result<ExamList> result = new Result<ExamList>();
        ExamList examListEntity = examListService.getById(examList.getId());
        if (examListEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = examListService.updateById(examList);
            //TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        try {
            examListService.removeById(id);
        } catch (Exception e) {
            log.error("删除失败", e.getMessage());
            return Result.error("删除失败!");
        }
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatch")
    public Result<ExamList> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<ExamList> result = new Result<ExamList>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.examListService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryById")
    public Result<ExamList> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<ExamList> result = new Result<ExamList>();
        ExamList examList = examListService.getById(id);
        if (examList == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(examList);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ExamList examList) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<ExamList> queryWrapper = QueryGenerator.initQueryWrapper(examList, request.getParameterMap());
        List<ExamList> pageList = examListService.list(queryWrapper);
        // Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        // 过滤选中数据
        String selections = request.getParameter("selections");
        if (oConvertUtils.isEmpty(selections)) {
            mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            List<ExamList> exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
            mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        }
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "考试列表列表");
        mv.addObject(NormalExcelConstants.CLASS, ExamList.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("考试列表列表数据", "导出人:Jeecg", "导出信息"));
        return mv;
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<ExamList> listExamLists = ExcelImportUtil.importExcel(file.getInputStream(), ExamList.class, params);
                examListService.saveBatch(listExamLists);
                return Result.ok("文件导入成功！数据行数:" + listExamLists.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.ok("文件导入失败！");
    }

}
