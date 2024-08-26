package com.ls.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Question;
import com.ls.service.QuestionService;
import com.ls.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author 32093
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2024-08-26 10:28:45
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




