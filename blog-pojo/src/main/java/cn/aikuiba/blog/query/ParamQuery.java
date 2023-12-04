package cn.aikuiba.blog.query;

import cn.aikuiba.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by 蛮小满Sama at 2023/11/20 23:29
 *
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ParamQuery extends BaseQuery {

    /*排序*/
    private String orderBy = "";
}
