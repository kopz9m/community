package life.majiang.community.service;


import life.majiang.community.dto.FileResult;
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

    @Value("${server.address}")
    private String host;
    @Value("${server.port}")
    private String port;

    // 通过http url 上传图片
    public FileResult upload(String url) {
        File newFile = FileUtils.newFile(url);
        assert newFile != null;
        FileResult fileResult;
        try {
            fileResult = upload(new FileInputStream(newFile), "image/png", newFile.getName());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("new file exception", e);
        }
        FileUtils.deleteFile(newFile);
        return fileResult;
    }

    // 上传图片，写入目录
    public FileResult upload(InputStream fileStream, String mimeType, String fileName) {
        // 重命名
        String newFileName = FileUtils.newUUIDPNGFileName();
        System.out.println(mimeType);

        // 存入目录
        try {
            Files.copy(fileStream, Paths.get(FileUtils.path).resolve(newFileName));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

        FileResult fileResult = new FileResult();
        fileResult.setFileName(newFileName);
        fileResult.setFileUrl("http://" + host + ":" + port + "/file/get/" + newFileName);
        return fileResult;
    }

    // 加载图片
    public Resource load(String filename) {
        try {
            Path file = Paths.get(FileUtils.path + filename);
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

