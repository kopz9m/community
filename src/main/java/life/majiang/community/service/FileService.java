package life.majiang.community.service;


import life.majiang.community.dto.FileResult;
import life.majiang.community.enums.FileTypeEnum;
import life.majiang.community.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class FileService {

    @Value("${myHost}")
    private String host;
    @Value("${server.port}")
    private String port;

    // 上传图片，写入目录
    public FileResult upload(InputStream fileStream, String type, String fileName) {
        // 重命名
        String newFileName = FileUtils.newUUIDFileName(fileName);
        String dateFolder = FileUtils.folderByDate();
        Path path = null;
        if (type.equals(FileTypeEnum.IMAGE.getType())){
            path = Paths.get(FileUtils.getImagesPath() + dateFolder)
                    .resolve(newFileName);
        } else {
            // todo
        }
        // 存入目录
        try {
            Files.copy(fileStream, path);
        } catch (Exception e) {
            throw new RuntimeException("无法上传图片: " + e.getMessage());
        }

        FileResult fileResult = new FileResult();
        fileResult.setFileName(newFileName);
        fileResult.setFileUrl("http://" + host + ":" + port + "/file/get/?folder=" + dateFolder
                + "&filename=" + newFileName);
        return fileResult;
    }

    // 加载图片
    public Resource load(String type, String folder, String filename) {
        Path file = null;
        // 根据头像或者图片设置文件路径
        if (type.equals(FileTypeEnum.AVATAR.getType())) {
            file = Paths.get(FileUtils.getAvatarsPath() + filename);
        } else if (type.equals(FileTypeEnum.IMAGE.getType())) {
            file = Paths.get(FileUtils.getImagesPath() + folder + System.getProperty("file.separator") + filename);
        }

        try {
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("图片不存在：" + file.toString());
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}

