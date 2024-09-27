package com.ls.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ls.model.entity.Question;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author 32093
 * @description 针对表【question(题目)】的数据库操作Mapper
 * @createDate 2024-08-26 10:28:45
 * @Entity com.ls.model.entity.Question
 */
public interface QuestionMapper extends BaseMapper<Question> {

    @Select("select * from mianshiya.question where updateTime >= #{fiveMinutesAgoDate}")
    List<Question> listQuestionWithDelete(Date fiveMinutesAgoDate);
}




