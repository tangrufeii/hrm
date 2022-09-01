package cn.itsource.service;

import cn.itsource.result.JSONResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    JSONResult uploadImg(MultipartFile multipartFile);

    JSONResult deleteFile(String ossKey);
}
