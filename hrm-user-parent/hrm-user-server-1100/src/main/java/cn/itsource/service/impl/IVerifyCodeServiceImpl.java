package cn.itsource.service.impl;

import cn.itsource.dto.SendSMSDto;
import cn.itsource.exception.MyException;
import cn.itsource.result.JSONResult;
import cn.itsource.service.IVerifyCodeService;
import cn.itsource.utils.MyTools;
import cn.itsource.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @auth: wujiangbo
 * @date: 2022-02-12 11:37
 */
@Service
public class IVerifyCodeServiceImpl  implements IVerifyCodeService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 逻辑：
     * 1、校验key
     * 2、生成随机的四位数，并且存入Redis（key就是入参key）
     * 3、生成图片，并将随机数水印上去
     * 4、将图片进行base64编码，结果返回
     */
    @Override
    public String createImageCode(String key) {
        if(!StringUtils.hasLength(key)){
            throw new MyException("UUID不能为空");
        }

        //生成随时4位数
        String code = MyTools.getRandomNum(4);

        //存入redis
        redisTemplate.opsForValue().set(key, code, 2, TimeUnit.MINUTES);

        //生成图片Base64数据
        String imgBase64 = VerifyCodeUtils.verifyCode(130, 35, code);
        return imgBase64;
    }

    @Override
    public JSONResult sendSmsCode(SendSMSDto dto) {
        //1、校验入参

        //2、先查询数据库，看手机号是否注册

        //3、校验图片验证码输入是否正确
        String code = (String)redisTemplate.opsForValue().get(dto.getImageCodeKey());
        if(!StringUtils.hasLength(code)){
            throw new MyException("请点击图片验证码刷新");
        }
        if(!code.equalsIgnoreCase(dto.getImageCode())){
            throw new MyException("验证输入错误，请重新输入");
        }
        //4、发短信
        String smsCode = MyTools.getRandomNum(6);
        String msg = "尊敬的：[" + dto.getMobile() + "]，欢迎注册XXX系统，您的注册验证码是：" + smsCode;
        //调用接口发送短信
        System.out.println(msg);
        //将验证码存入Redis
        redisTemplate.opsForValue().set(dto.getMobile(), smsCode, 60, TimeUnit.SECONDS);
        return JSONResult.success();
    }
}
