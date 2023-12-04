package cn.aikuiba.blog.repository;

import cn.aikuiba.blog.entity.ArticleComment;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/25 10:08
 *
 * @description Repository 注解:交给Spring管理
 */
@Repository
public interface ICommentRepository extends MongoRepository<ArticleComment, String> {

    /**
     * 根据文章Id查询所有的评论(未分页)
     * findArticleCommentBy:固定写法
     * By后面的必须是实体类里面的字段(属性),有代码提示
     *
     * @param articleId 文章Id
     * @return
     */
    List<ArticleComment> findArticleCommentByArticleId(Long articleId);
}
