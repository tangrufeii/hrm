package cn.itsource.dto;

import lombok.Data;

/**
 * @description:
 * @auth: tangrufei
 * @date: 2022-08-20
 */
@Data
public class SendSMSDto {

    private String mobile;
    private String imageCode;
    private String imageCodeKey;
}
