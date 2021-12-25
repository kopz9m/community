package life.majiang.community.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.io.FileUtils.copyURLToFile;

// 用于处理图片
@Slf4j
public class FileUtils {

    // 图片存放根目录
    public static String path = System.getProperty("user.home") + System.getProperty("file.separator")
            + "Pictures" + System.getProperty("file.separator") + "majiang" + System.getProperty("file.separator");


    private static List<String> suffix = new ArrayList<>();

    static {
        suffix.add(".png");
        suffix.add(".bmp");
        suffix.add(".gif");
        suffix.add(".jpeg");
        suffix.add(".jpg");
    }

    // 检查文件后缀，是否为图片格式
    public static boolean hasSuffix(String fileName) {
        for (String s : suffix) {
            if (StringUtils.endsWithIgnoreCase(fileName, s)) {
                return true;
            }
        }
        return false;
    }

    // 用 原文件名 + UUID 重命名文件名
    public static String newUUIDFileName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return fileName;
        }
        if (hasSuffix(fileName)) {
            String[] filePaths = fileName.split("\\.");
            if (filePaths.length > 1) {
                return UUID.randomUUID().toString().replace("-", "") + "." + filePaths[filePaths.length - 1];
            } else {
                return newUUIDPNGFileName();
            }
        } else {
            return newUUIDPNGFileName();
        }
    }

    // 用 UUID 生成文件名
    public static String newUUIDPNGFileName() {
        return UUID.randomUUID().toString().replace("-", "") + ".png";
    }

    // 返回系统用户路径 + 新的文件名
    public static String newLocalFileName(String fileName) {
        //String path = System.getProperty("user.dir") + File.separator;
        return newLocalFileName(path, fileName);
    }

    // 组合 path 和文件名
    public static String newLocalFileName(String path, String fileName) {
        return path + newUUIDFileName(fileName);
    }

    // 将 url 的图片写入文件
    public static File newFile(String url) {
        File file;
        String localFile = newLocalFileName(url);
        log.info("FILE_UTILS_NEW_INFO, url : {}, localFile : {}", url, localFile);
        file = new File(localFile);
        try {
            copyURLToFile(new URL(url), file);
        } catch (Exception e) {
            log.error("FILE_UTILS_NEW_ERROR, url : {}", url, e);
            return null;
        }
        return file;
    }

    // 删除图片
    public static void deleteFile(File file) {
        try {
            file.delete();
        } catch (Exception e) {
            log.debug("FILE_UTILS_ERROR", e);
        }
    }


    //test
    public static void main(String[] args) {
//        String x = FileUtils.newLocalFileName("http://luckydraw.cn-bj.ufileos.com/ffb5134b-8070-4d65-be0f-c3b1a0050dbc.jpg");
       System.out.println(path);


    }
}