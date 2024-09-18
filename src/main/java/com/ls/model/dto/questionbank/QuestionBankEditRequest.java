package com.ls.model.dto.questionbank;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
* title: 题库
* author: liaoshuo
* package: com.ls
* date: 2024-09-04
* description: 编辑题库请求
*/
@Data
public class QuestionBankEditRequest implements Serializable {

    /**
     * id
     */
    private Long id;

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

    private static final long serialVersionUID = 1L;
}