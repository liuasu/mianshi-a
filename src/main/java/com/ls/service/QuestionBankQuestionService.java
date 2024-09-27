package com.ls.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ls.model.dto.questionbankquestion.QuestionBankQuestionBatchAddRequest;
import com.ls.model.dto.questionbankquestion.QuestionBankQuestionBatchRemoveRequest;
import com.ls.model.dto.questionbankquestion.QuestionBankQuestionQueryRequest;
import com.ls.model.entity.QuestionBankQuestion;
import com.ls.model.entity.User;
import com.ls.model.vo.QuestionBankQuestionVO;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * title: 题库题目
 * author: liaoshuo
 * package: com.ls
 * date: 2024-09-04
 * description: 题库题目服务
 */
public interface QuestionBankQuestionService extends IService<QuestionBankQuestion> {

    /**
     * 校验数据
     *
     * @param questionBankQuestion
     * @param add                  对创建的数据进行校验
     */
    void validQuestionBankQuestion(QuestionBankQuestion questionBankQuestion, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionBankQuestionQueryRequest
     * @return
     */
    QueryWrapper<QuestionBankQuestion> getQueryWrapper(QuestionBankQuestionQueryRequest questionBankQuestionQueryRequest);

    /**
     * 获取题库题目封装
     *
     * @param questionBankQuestion
     * @param request
     * @return
     */
    QuestionBankQuestionVO getQuestionBankQuestionVO(QuestionBankQuestion questionBankQuestion, HttpServletRequest request);

    /**
     * 分页获取题库题目封装
     *
     * @param questionBankQuestionPage
     * @param request
     * @return
     */
    Page<QuestionBankQuestionVO> getQuestionBankQuestionVOPage(Page<QuestionBankQuestion> questionBankQuestionPage, HttpServletRequest request);

    /**
     * 批量添加题库问题
     *
     * @param batchAddRequest 批量添加请求
     * @param loginUser       登录用户
     */
    void batchAddQuestionBankQuestion(QuestionBankQuestionBatchAddRequest batchAddRequest, User loginUser);

    /**
     * 批量删除问题库问题
     *
     * @param batchRemoveRequest 批量删除请求
     */
    void batchRemoveQuestionBankQuestion(QuestionBankQuestionBatchRemoveRequest batchRemoveRequest);

    /**
     * 批量添加问题到 Bank Inner
     *
     * @param questionBankQuestions 问题库问题
     */
    @Transactional(rollbackFor = Exception.class)
    void batchAddQuestionsToBankInner(List<QuestionBankQuestion> questionBankQuestions);
}
