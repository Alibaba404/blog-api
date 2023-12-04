package cn.aikuiba.blog.service;

import cn.aikuiba.blog.entity.ArticleType;
import cn.aikuiba.blog.query.ArticleTypeQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/18 10:33
 *
 * @description
 */
public interface IArticleTypeService {

    List<ArticleType> findAll();

    ArticleType findOne(Long uid);

    void saveOne(ArticleType ArticleType);

    void delete(Long uid);

    void update(ArticleType ArticleType);

    void delBatch(Long[] ids);

    PageInfo<ArticleType> page(ArticleTypeQuery ArticleTypeQuery);

    /**
     * 根据用户名查询用户
     *
     * @param ArticleTypeName 用户名
     * @return
     */
    ArticleType findOneByArticleTypeName(String ArticleTypeName);

    List<ArticleType> allArticleType();

}
