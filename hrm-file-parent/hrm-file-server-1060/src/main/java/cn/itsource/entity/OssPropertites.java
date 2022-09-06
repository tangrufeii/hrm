package cn.itsource.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @auth: tangrufei
 * @date: 2022-08-20 11:10
 */
@Component
@Data
@ConfigurationProperties(prefix = "file.alicloud")
public class OssPropertites {

    private String bucketName;
    private String accessKey;
    private String secretKey;
    private String endpoint;
}
