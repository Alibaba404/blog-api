package cn.aikuiba.blog.service;

import cn.aikuiba.blog.dto.ArticleDto;
import cn.aikuiba.blog.entity.Article;
import cn.aikuiba.blog.query.ArticleQuery;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 蛮小满Sama at 2023/11/18 10:33
 *
 * @description
 */
public interface IArticleService {

    List<Article> findAll();

    Article findOne(Long id,HttpServletRequest request);

    void saveOne(Article article);

    void delete(Long id);

    void update(Article article);

    void delBatch(Long[] ids);

    PageInfo<Article> page(ArticleQuery articleQuery);

    List<ArticleDto> getCountArticleType();

    List<ArticleDto> getCountArticle();

    List<Article> getRelatedArticles(Long articleId);

    List<Article> getArticleByArticleType(Long articleTypeId);

    List<Article> getArticleArchivist(String time);

    Map<String, Object> getTopStarNum();

    List<ArticleDto> getTopCountArticleType(Integer topCount);

    void addCommentCount(Long articleId);

    void subtractCommentCount(Long articleId);

    void addArticleReadCount(Long articleId);

    Integer addArticleStarNum(Long articleId, HttpServletRequest request);

    Boolean getIsStarClick(Long articleId, HttpServletRequest request);

    Integer subArticleStarNum(Long articleId, HttpServletRequest request);
}
