package cn.itsource.result;

import cn.itsource.vo.AggTermsVo;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 用来封装分页查询结果集
 */
@Data
public class PageList<T> {

    //条数
    private Long total = 0l;

    //列表
    private List<T> rows = new ArrayList<>();

    //聚合
    private Map<String, List<AggTermsVo>> aggrMap = new HashMap<>();

    public PageList() {
    }

    public PageList(Long total, List<T> rows, Map<String, List<AggTermsVo>> aggrMap) {
        this.total = total;
        this.rows = rows;
        this.aggrMap = aggrMap;
    }

    public PageList(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
}
