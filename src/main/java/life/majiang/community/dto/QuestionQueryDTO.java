package life.majiang.community.dto;

import lombok.Data;

// 用来接受来自前端的请求信息。
@Data
public class QuestionQueryDTO {
    private String search;
    private String sort;
    private Long time;
    private String tag;
    private Integer page;
    private Integer size;
}
