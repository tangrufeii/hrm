package cn.itsource.dto;

import lombok.Data;
import java.util.Date;

@Data
public class LoginMealDto {

    private Long mealId;
    private Long loginId;
    private Integer state;
    private Date expireDate;
}
