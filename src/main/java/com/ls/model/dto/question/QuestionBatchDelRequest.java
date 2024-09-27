package com.ls.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * title: 题目
 * author: liaoshuo
 * package: com.ls
 * date: 2024-09-04
 * description: 批量删除题目请求
 */
@Data
public class QuestionBatchDelRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 题目 id
     */
    private List<Long> questionIdList;
}