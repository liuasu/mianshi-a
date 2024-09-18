package com.ls.model.dto.questionbank;

import com.ls.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * title: 题库
 * author: liaoshuo
 * package: com.ls
 * date: 2024-09-04
 * description: 题库视图
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionBankQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * id
     */
    private Long notId;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 图片
     */
    private String picture;

    /**
     * 是否查询题目列表
     */
    private boolean needQueryQuestionList = false;

    /**
     * 创建用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}