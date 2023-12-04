package cn.aikuiba.blog.service.impl;

import cn.aikuiba.blog.entity.ArticleType;
import cn.aikuiba.blog.mapper.ArticleTypeMapper;
import cn.aikuiba.blog.query.ArticleTypeQuery;
import cn.aikuiba.blog.service.IArticleTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 蛮小满Sama at 2023/11/18 10:34
 *
 * @description
 */
@CacheConfig(cacheNames = "ArticleType")    //SpringCache缓存配置
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
@Service
public class ArticleTypeServiceImpl implements IArticleTypeService {
    // Redis缓存的Key
    //    private static final String REDIS_KEY = "ArticleTypeTree";

    // SpringCache缓存的Key:必须使用''
    private static final String REDIS_KEY = "'TypeTree'";

    @Autowired
    private ArticleTypeMapper articleTypeMapper;

    @Override
    public List<ArticleType> findAll() {
        return articleTypeMapper.findAll();
    }

    @Override
    public ArticleType findOne(Long uid) {
        return articleTypeMapper.findOne(uid);
    }

    @CacheEvict(key = REDIS_KEY)
    @Transactional
    @Override
    public void saveOne(ArticleType articleType) {
        ArticleType one = this.findOneByArticleTypeName(articleType.getTypeName());
        if (null != one) {
            throw new RuntimeException("用户名已存在!");
        }
        articleTypeMapper.insert(articleType);
//        redisTemplate.delete(REDIS_KEY);
    }

    @CacheEvict(key = REDIS_KEY)
    @Transactional
    @Override
    public void delete(Long uid) {
        articleTypeMapper.delete(uid);
//        redisTemplate.delete(REDIS_KEY);
    }

    @CacheEvict(key = REDIS_KEY)
    @Transactional
    @Override
    public void update(ArticleType articleType) {
        articleType.setUpdateTime(new Date());
        articleTypeMapper.update(articleType);
//        redisTemplate.delete(REDIS_KEY);
    }

    @CacheEvict(key = REDIS_KEY)
    @Override
    public void delBatch(Long[] ids) {
        articleTypeMapper.deleteBatch(ids);
//        redisTemplate.delete(REDIS_KEY);
    }

    @Override
    public PageInfo<ArticleType> page(ArticleTypeQuery articleTypeQuery) {
        // 当前页码
        Integer currentPage = articleTypeQuery.getCurrentPage();
        // 分页条数
        Integer pageSize = articleTypeQuery.getPageSize();
        // 开启分页,必须在业务分页查询方法的前面,Mybatis才会拦截
        PageHelper.startPage(currentPage, pageSize);
        return new PageInfo<>(articleTypeMapper.page(articleTypeQuery));
    }

    /**
     * 根据用户名查询用户
     *
     * @param articleTypeName 用户名
     * @return
     */
    @Override
    public ArticleType findOneByArticleTypeName(String articleTypeName) {
        return articleTypeMapper.findOneByArticleTypeName(articleTypeName);
    }

    @Cacheable(key = REDIS_KEY)
    @Override
    public List<ArticleType> allArticleType() {
        return getAllArticleType();
    }

/*    // Redis独享版
       public List<ArticleType> allArticleType() {
        Object tree = redisTemplate.opsForValue().get(REDIS_KEY);
        if (null == tree) {
            List<ArticleType> allArticleType = this.getAllArticleType();
            redisTemplate.opsForValue().set(REDIS_KEY, allArticleType);
            return allArticleType;
        }
        return (List<ArticleType>) tree;
    }*/

    private List<ArticleType> getAllArticleType() {
        List<ArticleType> articleTypeTree = new ArrayList<>();
        List<ArticleType> allArticleType = this.findAll();
        if (null != allArticleType && allArticleType.size() > 0) {
            Map<Long, ArticleType> map = new HashMap<>();
            for (ArticleType articleType : allArticleType) {
                map.put(articleType.getId(), articleType);
            }

            for (ArticleType articleType : allArticleType) {
                // 当前类型的父级id
                Long parentId = articleType.getParentId();
                if (null == parentId) { // 没有父级,顶级文章类型
                    articleTypeTree.add(articleType);
                } else {// 当前文章类型不是顶级类型
                    // 通过父级id去查父级
                    ArticleType parentArticleType = map.get(parentId);
                    if (null != parentArticleType) {
                        parentArticleType.getChildren().add(articleType);
                    }
                }
            }

        }
        return articleTypeTree;
    }
}
