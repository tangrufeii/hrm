package cn.itsource.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 用来封装Terms这种聚合结果的VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggTermsVo {

    private String key;//名称
    private Long docCount;//课程的数量
}
