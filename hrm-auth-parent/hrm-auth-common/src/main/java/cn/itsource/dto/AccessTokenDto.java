package cn.itsource.dto;

import lombok.Data;

@Data
public class AccessTokenDto {

    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private Integer expiresIn;
    private String scope;
    private String jti;
}
