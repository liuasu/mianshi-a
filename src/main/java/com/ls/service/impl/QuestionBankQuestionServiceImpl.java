package com.ls.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ls.common.ErrorCode;
import com.ls.constant.CommonConstant;
import com.ls.exception.BusinessException;
import com.ls.exception.ThrowUtils;
import com.ls.mapper.QuestionBankQuestionMapper;
import com.ls.model.dto.questionbankquestion.QuestionBankQuestionBatchAddRequest;
import com.ls.model.dto.questionbankquestion.QuestionBankQuestionBatchRemoveRequest;
import com.ls.model.dto.questionbankquestion.QuestionBankQuestionQueryRequest;
import com.ls.model.entity.Question;
import com.ls.model.entity.QuestionBank;
import com.ls.model.entity.QuestionBankQuestion;
import com.ls.model.entity.User;
import com.ls.model.vo.QuestionBankQuestionVO;
import com.ls.model.vo.UserVO;
import com.ls.service.QuestionBankQuestionService;
import com.ls.service.QuestionBankService;
import com.ls.service.QuestionService;
import com.ls.service.UserService;
import com.ls.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * title: 题库题目
 * author: liaoshuo
 * package: com.ls
 * date: 2024-09-04
 * description: 题库题目服务实现
 */
@Service
@Slf4j
public class QuestionBankQuestionServiceImpl extends ServiceImpl<QuestionBankQuestionMapper, QuestionBankQuestion> implements QuestionBankQuestionService {

    @Resource
    private UserService userService;

    @Lazy
    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionBankService qbService;

    /**
     * 校验数据
     *
     * @param questionBankQuestion
     * @param add                  对创建的数据进行校验
     */
    @Override
    public void validQuestionBankQuestion(QuestionBankQuestion questionBankQuestion, boolean add) {
        ThrowUtils.throwIf(questionBankQuestion == null, ErrorCode.PARAMS_ERROR);
        Long questionBankId = questionBankQuestion.getQuestionBankId();
        Long questionId = questionBankQuestion.getQuestionId();
        if (questionBankId != null) {
            QuestionBank questionBank = qbService.getById(questionBankId);
            ThrowUtils.throwIf(questionBank == null, ErrorCode.NOT_FOUND_ERROR, "题库不存在");
        }
        if (questionId != null) {
            Question question = questionService.getById(questionId);
            ThrowUtils.throwIf(question == null, ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
    }

    /**
     * 获取查询条件
     *
     * @param questionBankQuestionQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionBankQuestion> getQueryWrapper(QuestionBankQuestionQueryRequest questionBankQuestionQueryRequest) {


        QueryWrapper<QuestionBankQuestion> queryWrapper = new QueryWrapper<>();
        if (questionBankQuestionQueryRequest == null) {
            return queryWrapper;
        }
        // todo 从对象中取值
        Long id = questionBankQuestionQueryRequest.getId();
        Long notId = questionBankQuestionQueryRequest.getNotId();
        Long questionBankId = questionBankQuestionQueryRequest.getQuestionBankId();
        Long questionId = questionBankQuestionQueryRequest.getQuestionId();
        Long userId = questionBankQuestionQueryRequest.getUserId();

        String sortField = questionBankQuestionQueryRequest.getSortField();
        String sortOrder = questionBankQuestionQueryRequest.getSortOrder();
        // 精确查询
        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionBankId), "questionBankId", questionBankId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }

    /**
     * 获取题库题目封装
     *
     * @param questionBankQuestion
     * @param request
     * @return
     */
    @Override
    public QuestionBankQuestionVO getQuestionBankQuestionVO(QuestionBankQuestion questionBankQuestion, HttpServletRequest request) {
        // 对象转封装类
        QuestionBankQuestionVO questionBankQuestionVO = QuestionBankQuestionVO.objToVo(questionBankQuestion);

        // todo 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 1. 关联查询用户信息
        Long userId = questionBankQuestion.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        questionBankQuestionVO.setUser(userVO);
        // endregion

        return questionBankQuestionVO;
    }

    /**
     * 分页获取题库题目封装
     *
     * @param questionBankQuestionPage
     * @param request
     * @return
     */
    @Override
    public Page<QuestionBankQuestionVO> getQuestionBankQuestionVOPage(Page<QuestionBankQuestion> questionBankQuestionPage, HttpServletRequest request) {
        List<QuestionBankQuestion> questionBankQuestionList = questionBankQuestionPage.getRecords();
        Page<QuestionBankQuestionVO> questionBankQuestionVOPage = new Page<>(questionBankQuestionPage.getCurrent(), questionBankQuestionPage.getSize(), questionBankQuestionPage.getTotal());
        if (CollUtil.isEmpty(questionBankQuestionList)) {
            return questionBankQuestionVOPage;
        }
        // 对象列表 => 封装对象列表
        List<QuestionBankQuestionVO> questionBankQuestionVOList = questionBankQuestionList.stream().map(questionBankQuestion -> {
            return QuestionBankQuestionVO.objToVo(questionBankQuestion);
        }).collect(Collectors.toList());

        // todo 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 1. 关联查询用户信息
        Set<Long> userIdSet = questionBankQuestionList.stream().map(QuestionBankQuestion::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream().collect(Collectors.groupingBy(User::getId));

        // 填充信息
        questionBankQuestionVOList.forEach(questionBankQuestionVO -> {
            Long userId = questionBankQuestionVO.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            questionBankQuestionVO.setUser(userService.getUserVO(user));
        });
        // endregion

        questionBankQuestionVOPage.setRecords(questionBankQuestionVOList);
        return questionBankQuestionVOPage;
    }


    /**
     * 批量添加题库问题
     *
     * @param batchAddRequest 批量添加请求
     * @param loginUser       登录用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAddQuestionBankQuestion(QuestionBankQuestionBatchAddRequest batchAddRequest, User loginUser) {
        //校验参数
        Long questionBankId = batchAddRequest.getQuestionBankId();
        List<Long> questionIdList = batchAddRequest.getQuestionIdList();
        ThrowUtils.throwIf(questionBankId <= 0, ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(CollectionUtil.isEmpty(questionIdList), ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(loginUser == null, ErrorCode.NOT_LOGIN_ERROR);
        //判断 题目/题库是否存在
        LambdaQueryWrapper<Question> wrapper = Wrappers.lambdaQuery(Question.class).select(Question::getId).in(Question::getId, questionIdList);
        List<Long> validQuestionIdList = questionService.listObjs(wrapper, o -> (Long) o);
        //List<Question> questionList =
        QuestionBankQuestion bankQuestion = this.getById(questionBankId);
        ThrowUtils.throwIf(CollectionUtil.isEmpty(validQuestionIdList), ErrorCode.OPERATION_ERROR, "题目列表为空");
        ThrowUtils.throwIf(bankQuestion == null, ErrorCode.OPERATION_ERROR, "题库不存在");

        //查询不在题库题目关系中的数据
        List<QuestionBankQuestion> bankQuestions = lambdaQuery().eq(QuestionBankQuestion::getId, questionBankId).notIn(QuestionBankQuestion::getQuestionId, validQuestionIdList).list();
        //已存在题库中的id
        Set<Long> collect = bankQuestions.stream().map(QuestionBankQuestion::getId).collect(Collectors.toSet());
        //过滤出不在题库中的题目
        validQuestionIdList = validQuestionIdList.stream().filter(questionId -> !collect.contains(questionId)).collect(Collectors.toList());
        ThrowUtils.throwIf(CollectionUtil.isEmpty(validQuestionIdList), ErrorCode.OPERATION_ERROR, "所有题目已在题库中");


        // 分批处理避免长事务，假设每次处理 1000 条数据
        int batchSize = 1000;
        int totalQuestionListSize = validQuestionIdList.size();
        for (int i = 0; i < totalQuestionListSize; i += batchSize) {
            // 生成每批次的数据
            List<Long> subList = validQuestionIdList.subList(i, Math.min(i + batchSize, totalQuestionListSize));
            List<QuestionBankQuestion> questionBankQuestions = subList.stream().map(questionId -> {
                QuestionBankQuestion questionBankQuestion = new QuestionBankQuestion();
                questionBankQuestion.setQuestionBankId(questionBankId);
                questionBankQuestion.setQuestionId(questionId);
                questionBankQuestion.setUserId(loginUser.getId());
                return questionBankQuestion;
            }).collect(Collectors.toList());
            // 使用事务处理每批数据
            QuestionBankQuestionService questionBankQuestionService = (QuestionBankQuestionServiceImpl) AopContext.currentProxy();
            questionBankQuestionService.batchAddQuestionsToBankInner(questionBankQuestions);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAddQuestionsToBankInner(List<QuestionBankQuestion> questionBankQuestions) {
        try {
            boolean result = this.saveBatch(questionBankQuestions);
            ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR, "向题库添加题目失败");
        } catch (DataIntegrityViolationException e) {
            log.error("数据库唯一键冲突或违反其他完整性约束, 错误信息: {}", e.getMessage());
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目已存在于该题库，无法重复添加");
        } catch (DataAccessException e) {
            log.error("数据库连接问题、事务问题等导致操作失败, 错误信息: {}", e.getMessage());
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "数据库操作失败");
        } catch (Exception e) {
            // 捕获其他异常，做通用处理
            log.error("添加题目到题库时发生未知错误，错误信息: {}", e.getMessage());
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "向题库添加题目失败");
        }
    }


    /**
     * 批量删除问题库问题
     *
     * @param batchRemoveRequest 批量删除请求
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchRemoveQuestionBankQuestion(QuestionBankQuestionBatchRemoveRequest batchRemoveRequest) {
        //校验参数
        Long questionBankId = batchRemoveRequest.getQuestionBankId();
        List<Long> questionIdList = batchRemoveRequest.getQuestionIdList();
        ThrowUtils.throwIf(questionBankId <= 0, ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(CollectionUtil.isEmpty(questionIdList), ErrorCode.PARAMS_ERROR);
        for (Long questionId : questionIdList) {
            boolean remove = lambdaUpdate().eq(QuestionBankQuestion::getQuestionBankId, questionBankId).eq(QuestionBankQuestion::getQuestionBankId, questionId).remove();
            ThrowUtils.throwIf(!remove, ErrorCode.OPERATION_ERROR, "向题库删除题目失败");
        }
    }
}
