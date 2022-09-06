package cn.itsource.dto;

import cn.itsource.domain.Employee;
import cn.itsource.domain.Tenant;
import lombok.Data;

/**
 * @description:
 * @auth: tangrufei
 * @date: 2022-08-21 11:10
 */
@Data
public class RegisterDto {

    private Long mealId;
    private String password;
    private String username;

    private Employee employee;
    private Tenant tenant;
}