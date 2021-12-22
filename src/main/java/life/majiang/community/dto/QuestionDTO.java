package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;

// 用来接收来自数据库 QUESTION 表的搜索结果
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
