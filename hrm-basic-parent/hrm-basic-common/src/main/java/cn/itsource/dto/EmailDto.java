package cn.itsource.dto;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @auth: tangrufei
 * @date: 2022-08-24 10:25
 */
@Data
public class EmailDto {

    private List<String> emailAddress;//收件人邮箱地址
    private String title;//邮件标题
    private String content;//邮件内容
}
