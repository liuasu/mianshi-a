package com.ls.model.dto.questionbankquestion;

import lombok.Data;

import java.io.Serializable;


/**
* title: 题库题目
* author: liaoshuo
* package: com.ls
* date: 2024-09-04
* description: 移除题库题目请求
*/
@Data
public class QuestionBankQuestionRemoveRequest implements Serializable {

    /**
     * 题库 id
     */
    private Long questionBankId;

    /**
     * 题目 id
     */
    private Long questionId;

    private static final long serialVersionUID = 1L;
}