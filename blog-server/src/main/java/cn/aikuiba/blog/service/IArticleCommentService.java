package cn.aikuiba.blog.service;

import cn.aikuiba.blog.controller.PageBean;
import cn.aikuiba.blog.entity.ArticleComment;
import cn.aikuiba.blog.query.ArticleCommentQuery;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Stack;

/**
 * Created by 蛮小满Sama at 2023/11/18 10:33
 *
 * @description
 */
public interface IArticleCommentService {


    void saveOne(ArticleComment articleComment);

    void delete(String id);

    void update(ArticleComment articleComment);

    /**
     * 根据文章Id查询所有的评论(未分页)
     * findArticleCommentBy:固定写法
     * By后面的必须是实体类里面的字段(属性),有代码提示
     *
     * @return
     * @articleComment articleId 文章Id
     */
    List<ArticleComment> findArticleCommentByArticleId(Long articleId);

    Page<ArticleComment> getArticleCommentPage(ArticleCommentQuery articleCommentQuery);
}
