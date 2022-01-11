package life.majiang.community.cache;

import com.google.common.cache.Cache;
import com.google.common.collect.Lists;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuestionExtMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.cache.CacheBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class QuestionCache {
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;

    // 缓存置顶问题
    private static Cache<String, List<QuestionDTO>> cacheQuestions = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .removalListener(entity -> log.info("QUESTIONS_CACHE_REMOVE:{}", entity.getKey()))
            .build();

    // 获取置顶问题
    public List<QuestionDTO> getStickies() {
        List<QuestionDTO> stickies;
        try {
            stickies = cacheQuestions.get("sticky", () -> {
                List<Question> questions = questionExtMapper.selectSticky();
                if (questions != null && questions.size() != 0) {
                    List<QuestionDTO> questionDTOS = new ArrayList<>();
                    for (Question question : questions) {
                        User user = userMapper.selectByPrimaryKey(question.getCreator());
                        QuestionDTO questionDTO = new QuestionDTO();
                        BeanUtils.copyProperties(question, questionDTO);
                        questionDTO.setUser(user);
                        questionDTO.setDescription("");
                        questionDTOS.add(questionDTO);
                    }
                    return questionDTOS;
                } else {
                    return Lists.newArrayList();
                }
            });
        } catch (Exception e) {
            log.error("getStickies error", e);
            return Lists.newArrayList();
        }
        return stickies;
    }
}
