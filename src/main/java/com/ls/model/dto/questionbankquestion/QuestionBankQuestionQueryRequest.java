package com.ls.model.dto.questionbankquestion;

import com.ls.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
* title: 题库题目
* author: liaoshuo
* package: com.ls
* date: 2024-09-04
* description: 题库题目视图
*/
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionBankQuestionQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * id
     */
    private Long notId;

    /**
     * 题库 id
     */
    private Long questionBankId;

    /**
     * 题目 id
     */
    private Long questionId;
    /**
     * 创建用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}