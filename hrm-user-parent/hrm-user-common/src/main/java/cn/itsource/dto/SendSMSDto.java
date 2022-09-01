package cn.itsource.dto;

import lombok.Data;

/**
 * @description:
 * @auth: wujiangbo
 * @date: 2022-02-12 14:22
 */
@Data
public class SendSMSDto {

    private String mobile;
    private String imageCode;
    private String imageCodeKey;
}
