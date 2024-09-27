package com.ls.esdao;

import com.ls.model.dto.post.PostEsDTO;
import com.ls.model.dto.question.QuestionEsDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * title: QuestionEsDao
 * author: liaoshuo
 * package: com.ls.esdao
 * date: 2024/9/25 15:56
 * description: 问题es操作
 */
public interface QuestionEsDao extends ElasticsearchRepository<QuestionEsDTO,Long> {

    List<PostEsDTO> findByUserId(Long userId);
}
