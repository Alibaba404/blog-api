package cn.aikuiba.blog.mapper;

import cn.aikuiba.blog.entity.ArticleType;
import cn.aikuiba.blog.query.ArticleTypeQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/20 11:09
 *
 * @description
 */
@Mapper
public interface ArticleTypeMapper {

    List<ArticleType> findAll();

    ArticleType findOne(Long uid);

    void insert(ArticleType ArticleType);

    void delete(Long uid);

    void update(ArticleType ArticleType);


    void deleteBatch(Long[] ids);

    List<ArticleType> page(ArticleTypeQuery articleTypeQuery);

    ArticleType findOneByArticleTypeName(String articleTypeName);
}
