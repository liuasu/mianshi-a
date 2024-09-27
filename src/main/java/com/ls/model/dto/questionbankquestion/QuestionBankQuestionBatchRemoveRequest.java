package com.ls.model.dto.questionbankquestion;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * title: 题库题目
 * author: liaoshuo
 * package: com.ls
 * date: 2024-09-04
 * description: 批量从题库移除题目请求
 */
@Data
public class QuestionBankQuestionBatchRemoveRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 题库 id
     */
    private Long questionBankId;
    /**
     * 题目 id
     */
    private List<Long> questionIdList;
}