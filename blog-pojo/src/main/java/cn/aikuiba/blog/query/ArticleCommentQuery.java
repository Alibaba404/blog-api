package cn.aikuiba.blog.query;

import cn.aikuiba.query.BaseQuery;
import lombok.Data;

/**
 * Created by 蛮小满Sama at 2023/12/4 10:51
 *
 * @description
 */
@Data
public class ArticleCommentQuery extends BaseQuery {
    /*文章Id*/
    private Long articleId;
}
