package cn.itsource.service;

import cn.itsource.dto.SendSMSDto;
import cn.itsource.result.JSONResult;

public interface IVerifyCodeService {
    String createImageCode(String key);

    JSONResult sendSmsCode(SendSMSDto dto);
}
