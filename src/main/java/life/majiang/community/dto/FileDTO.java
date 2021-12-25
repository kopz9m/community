package life.majiang.community.dto;

import lombok.Data;

/**
 * 主要将图片的 url 传入前端，嵌入html
 */
@Data
public class FileDTO {
    private int success;
    private String message;
    private String url;
}
