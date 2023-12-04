package cn.aikuiba.blog.mapper;

import cn.aikuiba.blog.dto.ArticleDto;
import cn.aikuiba.blog.entity.Article;
import cn.aikuiba.blog.query.ArticleQuery;
import cn.aikuiba.system.entity.IPAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/20 11:09
 *
 * @description
 */
@Mapper
public interface ArticleMapper {

    List<Article> findAll();

    Article findOne(Long uid);

    void insert(Article article);

    void delete(Long uid);

    void update(Article article);


    void deleteBatch(Long[] ids);

    List<Article> page(ArticleQuery articleTypeQuery);

    List<ArticleDto> countArticleType();

    List<ArticleDto> countArticle();

    List<Article> getRelatedArticles(Long articleId);

    List<Article> getArticleByArticleType(Long articleTypeId);

    List<Article> getArticleArchivist(String time);

    List<ArticleDto> getTopCountArticleType(Integer topCount);

    void increaseCommentCount(Long articleId);

    void decreaseCommentCount(Long articleId);

    void increaseArticleReadCount(Long articleId);

    IPAddress findIpAddressByArticleAndIp(@Param("articleId") Long articleId, @Param("ip") String ip);

    void increaseArticleStarNum(Long articleId);

    void insertIpAddress(IPAddress ipAddress);
}
