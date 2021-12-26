package life.majiang.community.controller;

import life.majiang.community.dto.FileDTO;
import life.majiang.community.dto.FileResult;
import life.majiang.community.enums.FileTypeEnum;
import life.majiang.community.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;

import static org.springframework.http.MediaType.IMAGE_GIF;

@Controller
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    // 上传图片
    @RequestMapping("/upload/{type}")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request,
                          @PathVariable(name = "type") String type) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");

        try {
            System.out.println("uploading image------");
            FileResult fileResult = fileService.upload(file.getInputStream(), type, file.getOriginalFilename());
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(fileResult.getFileUrl());
            return fileDTO;
        } catch (Exception e) {
            log.error("upload error", e);
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(0);
            fileDTO.setMessage("上传失败");
            return fileDTO;
        }
    }

    @GetMapping("/file/get")
    public ResponseEntity<Resource> getFile(
            @RequestParam(name = "type", required = true,defaultValue = "images") String type,
            @RequestParam(name = "folder", required = false) String folder,
            @RequestParam(name = "filename") String filename) {
        Resource file = fileService.load(type,folder,filename);
        return ResponseEntity
                .ok()
                .contentType(IMAGE_GIF)
                .body(file);
    }
}
