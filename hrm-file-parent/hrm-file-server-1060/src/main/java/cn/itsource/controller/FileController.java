package cn.itsource.controller;

import cn.itsource.entity.UploadVO;
import cn.itsource.result.JSONResult;
import cn.itsource.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: 处理文件上传
 * @auth: tangrufei
 * @date: 2022-08-20 10:45
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    //文件上传
    @PostMapping("/uploadFile")
    public JSONResult uploadImg(@RequestParam("fileName") MultipartFile multipartFile){
        return fileService.uploadImg(multipartFile);
    }

    @PostMapping("/deleteFile")
    public JSONResult deleteFile(@RequestBody UploadVO vo){
        return fileService.deleteFile(vo.getOssKey());
    }
}