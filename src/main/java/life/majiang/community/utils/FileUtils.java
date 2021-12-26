package life.majiang.community.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.commons.io.FileUtils.copyURLToFile;

// 用于处理图片
@Slf4j
public class FileUtils {
    // 文件存放根目录
    private final static String path = System.getProperty("user.home") + System.getProperty("file.separator")
            + "Pictures" + System.getProperty("file.separator") + "majiang" + System.getProperty("file.separator");
    // 头像目录
    private final static String avatarsPath = path + "avatars" + System.getProperty("file.separator");
    // 图片资源目录
    private final static String imagesPath = path + "images" + System.getProperty("file.separator");
    private final static String defaultAvatar = "default-avatar.png";

    // getter
    public static String getAvatarsPath() { return avatarsPath; }
    public static String getImagesPath() { return imagesPath; }
    public static String getDefaultAvatar() { return defaultAvatar; }

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

    // 用 原文件名 + UUID 重命名文件名, 保持文件后缀
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

    // 生成 UUID
    public static String newUUIDPNGFileName() {
        return UUID.randomUUID().toString().replace("-", "") + ".png";
    }

    // 删除图片
    public static void deleteFile(File file) {
        try {
            file.delete();
        } catch (Exception e) {
            log.debug("FILE_UTILS_ERROR", e);
        }
    }

    // 用当天日期作为目录
    public static String folderByDate() {
        Date date = new Date();
        String dateFolder = new SimpleDateFormat("yyyy_MM_dd").format(date);
        File dir = new File(imagesPath + dateFolder);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dateFolder;
    }

    // 默认头像地址
    public static String defaultAvatar(){
        return avatarsPath + "default-avatar.png";
    }

    // 随机生成6位数的验证码
    public static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }
    //test
    public static void main(String[] args) {

    }
}