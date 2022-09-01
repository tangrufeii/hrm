package cn.itsource.dto;

import cn.itsource.domain.Employee;
import cn.itsource.domain.Tenant;
import lombok.Data;

/**
 * @description:
 * @auth: wujiangbo
 * @date: 2022-01-17 14:38
 */
@Data
public class RegisterDto {

    private Long mealId;
    private String password;
    private String username;

    private Employee employee;
    private Tenant tenant;
}