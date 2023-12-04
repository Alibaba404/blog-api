package cn.aikuiba.query;

import lombok.Data;

/**
 * Created by 蛮小满Sama at 2023/11/20 11:26
 *
 * @description
 */
@Data
public class BaseQuery {

    /*当前页码*/
    private Integer currentPage = 1;

    /*分页条数*/
    private Integer pageSize = 5;

    /*搜索关键字*/
    private String keywords;

    /*排序字段和排序方式*/
    private String orderBy;
}
