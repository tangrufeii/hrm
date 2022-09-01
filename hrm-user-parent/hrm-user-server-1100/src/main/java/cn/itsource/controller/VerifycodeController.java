package cn.itsource.controller;

import cn.itsource.dto.SendSMSDto;
import cn.itsource.result.JSONResult;
import cn.itsource.service.IVerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//验证码相关
@RestController
@RequestMapping("/verifycode")
public class VerifycodeController {

    @Autowired
    private IVerifyCodeService verifyCodeService ;

    @PostMapping(value = "/sendSmsCode")
    public JSONResult sendSmsCode(@RequestBody SendSMSDto dto){
        return verifyCodeService.sendSmsCode(dto);
    }

    @GetMapping(value = "/imageCode/{key}")
    public JSONResult createImageCode(@PathVariable(value = "key") String key){
        try{
            String baseImageStr = verifyCodeService.createImageCode(key);
            return JSONResult.success(baseImageStr);//一张图片的Base64数据，并且这张图片里需要显示一个四位数的验证码
        }catch (Exception e){
            e.printStackTrace();
            return JSONResult.error(e.getMessage());
        }
    }
}
