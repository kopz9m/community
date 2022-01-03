package life.majiang.community.dto;

import lombok.Data;

@Data
public class FileResult {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件地址
     */
    private String fileUrl;
}
