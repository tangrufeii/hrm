package cn.itsource.service.impl;

import cn.itsource.constants.ErrorCode;
import cn.itsource.entity.OssPropertites;
import cn.itsource.entity.UploadVO;
import cn.itsource.result.JSONResult;
import cn.itsource.service.FileService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @description:
 * @auth: wujiangbo
 * @date: 2022-01-20 10:49
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private OssPropertites ossPropertites;

    /**
     * 文件上传到OSS
     */
    @Override
    public JSONResult uploadImg(MultipartFile multipartFile) {
        /**
         * 1、文件校验
         * 2、文件名称的处理
         * 3、上传oss，调用OSS的API
         * 4、封装相关参数（路径）返回
         */
        JSONResult returnVo = new JSONResult();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("image/png");
        strings.add("image/gif");
        strings.add("image/jpeg");
        //判断上传文件类型，做校验
        if (strings.contains(multipartFile.getContentType())){
            try {
                //获取文件名
                String fileName = multipartFile.getOriginalFilename();//123.jpg
                //获取文件类型
                String fileType = fileName.substring(fileName.lastIndexOf("."));//.jpg
                //uuid
                String uuid = UUID.randomUUID().toString().replace("-","");//hu23g4hj2gb423b4hj2b3hj4b32
                //company_logo/hu23g4hj2gb423b4hj2b3hj4b32.jpg
                String realName = "company_logo/" + uuid + fileType;

                OSS build = new OSSClientBuilder().build(ossPropertites.getEndpoint(), ossPropertites.getAccessKey(), ossPropertites.getSecretKey());
                /**
                 * 1、上传到哪个Bucket
                 * 2、文件名
                 * 3、文件本身
                 */
                PutObjectRequest putObjectRequest = new PutObjectRequest(ossPropertites.getBucketName(), realName, multipartFile.getInputStream());
                //将文件上传到OSS
                build.putObject(putObjectRequest);

                build.shutdown();//关闭OSSClient


                //拼接返回参数：文件全路径
                //https://hrm-0723.oss-cn-chengdu.aliyuncs.com/company_logo/33832b3375994a6ba8458d848b71bed2.jpg
                String imagePath = "https://" + ossPropertites.getBucketName()+"."+ossPropertites.getEndpoint()+"/"+realName;
                UploadVO vo = new UploadVO();
                vo.setOssKey(realName);
                vo.setFilePath(imagePath);
                return returnVo.success(vo);
            } catch (IOException e) {
                //发生异常
                e.printStackTrace();
                return returnVo.error("上传文件异常");
            }
        }else {
            //文件格式错误，非jpg/png/gif
            return returnVo.error("文件格式错误");
        }
    }

    @Override
    public JSONResult deleteFile(String ossKey) {
        OSS oss = new OSSClientBuilder().build(ossPropertites.getEndpoint(), ossPropertites.getAccessKey(), ossPropertites.getSecretKey());
        /**
         * 第二个参数：就是除了域名以外，后面的路径
         * https://hrm0507.oss-cn-chengdu.aliyuncs.com/itsource/797db90c-b555-4eb0-8067-9cf6fb9816d6_111.jpg
         * 这里的key就是：itsource/797db90c-b555-4eb0-8067-9cf6fb9816d6_111.jpg
         */
        oss.deleteObject(ossPropertites.getBucketName(), ossKey);
        oss.shutdown();//关闭OSSClient
        return JSONResult.success("文件删除成功");
    }
}
