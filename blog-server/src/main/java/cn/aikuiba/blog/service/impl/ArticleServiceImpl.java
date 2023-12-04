package cn.aikuiba.blog.service.impl;

import cn.aikuiba.blog.dto.ArticleDto;
import cn.aikuiba.blog.entity.Article;
import cn.aikuiba.blog.entity.ArticleComment;
import cn.aikuiba.blog.mapper.ArticleMapper;
import cn.aikuiba.blog.query.ArticleQuery;
import cn.aikuiba.blog.service.IArticleService;
import cn.aikuiba.exception.BisException;
import cn.aikuiba.system.entity.IPAddress;
import cn.aikuiba.utils.IPUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 蛮小满Sama at 2023/11/18 10:34
 *
 * @description
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
@Service
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public List<Article> findAll() {
        return articleMapper.findAll();
    }

    @Override
    public Article findOne(Long id, HttpServletRequest request) {
        Article article = articleMapper.findOne(id);
        article.setIsStarClick(this.getIsStarClick(id, request));
        return article;
    }

    @Transactional
    @Override
    public void saveOne(Article article) {
        article.setCreateTime(new Date());

        if (null == article.getArticleState()) {
            // 新增文章默认是草稿
            article.setArticleState(0);
        }
        // 给文章封面设置默认值,后期可能去掉
        String articlePic = article.getArticlePic();
        if (StrUtil.isBlank(articlePic)) {
            article.setArticlePic("http://files.aikuiba.cn:29000/d/G-AlistFiles/Share/Picture/%E4%B8%80%E4%BA%8C%E5%B8%83%E5%B8%83/001.jpeg");
        }
        // 设置发布人id
        article.setPublishId(1L);
        articleMapper.insert(article);

        // 新增到Mongodb数据库
        mongoTemplate.save(article);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        articleMapper.delete(id);
        // 删除单个
        mongoTemplate.remove(new Query(Criteria.where("id").is(id)), Article.class);
    }

    @Transactional
    @Override
    public void update(Article article) {
        article.setUpdateTime(new Date());
        articleMapper.update(article);
        // 新增到Mongodb数据库,有_id(已经隐射为id)就是更新
        mongoTemplate.save(article);
    }

    @Transactional
    @Override
    public void delBatch(Long[] ids) {
        articleMapper.deleteBatch(ids);
        // 找到并删除所有匹配的数据,返回被删除的数据
        Query query = new Query(Criteria.where("id").in(Arrays.asList(ids)));
        mongoTemplate.findAllAndRemove(query, Article.class).forEach(System.out::println);
    }

    @Override
    public PageInfo<Article> page(ArticleQuery articleQuery) {
        // 当前页码
        Integer currentPage = articleQuery.getCurrentPage();
        // 分页条数
        Integer pageSize = articleQuery.getPageSize();
        // 开启分页,必须在业务分页查询方法的前面,Mybatis才会拦截
        PageHelper.startPage(currentPage, pageSize);
        // 设置排序字段和规则
        PageHelper.orderBy(articleQuery.getOrderBy());
        return new PageInfo<>(articleMapper.page(articleQuery));
    }

    @Override
    public List<ArticleDto> getCountArticleType() {
        return articleMapper.countArticleType();
    }

    @Override
    public List<ArticleDto> getCountArticle() {
        return articleMapper.countArticle();
    }

    @Override
    public List<Article> getRelatedArticles(Long articleId) {
        return articleMapper.getRelatedArticles(articleId);
    }

    @Override
    public List<Article> getArticleByArticleType(Long articleTypeId) {
        return articleMapper.getArticleByArticleType(articleTypeId);
    }

    @Override
    public List<Article> getArticleArchivist(String time) {
        if ("all".equalsIgnoreCase(time)) time = "";
        List<Article> articles = articleMapper.getArticleArchivist(time);
        List<Article> result = new ArrayList<>();
        Map<String, List<Article>> listMap = articles.stream().collect(Collectors.groupingBy(Article::getYear));
        for (String year : listMap.keySet()) {
            Article article = new Article();
            article.setYear(year);
            article.setArticleList(listMap.get(year));
            result.add(article);
        }
        return result;
    }

    @Override
    public Map<String, Object> getTopStarNum() {
        ArticleQuery articleQuery = new ArticleQuery();
        articleQuery.setOrderBy("ta.article_star_num DESC");
        articleQuery.setPageSize(6);
        articleQuery.setArticleState(1);
        List<Article> list = this.page(articleQuery).getList();
        List<String> names = new ArrayList<>();
        List<Integer> stars = new ArrayList<>();
        list.forEach(article -> {
            names.add(article.getArticleName());
            stars.add(article.getArticleStarNum());
        });
        Map<String, Object> map = new HashMap<>();
        map.put("names", names);
        map.put("stars", stars);
        return map;
    }

    @Override
    public List<ArticleDto> getTopCountArticleType(Integer topCount) {
        return articleMapper.getTopCountArticleType(topCount);
    }

    @Override
    public void addCommentCount(Long articleId) {
        articleMapper.increaseCommentCount(articleId);
    }

    @Override
    public void subtractCommentCount(Long articleId) {
        articleMapper.decreaseCommentCount(articleId);
    }

    @Override
    public void addArticleReadCount(Long articleId) {
        articleMapper.increaseArticleReadCount(articleId);
    }

    @Override
    public Integer addArticleStarNum(Long articleId, HttpServletRequest request) {
        // 查询是否点过赞
        String ip = IPUtil.getClientIPAddress(request);
        IPAddress ipAddress = articleMapper.findIpAddressByArticleAndIp(articleId, ip);
        if (null == ipAddress) {
            // 该IP第一次点赞该文章
            // 增加文章点赞量
            articleMapper.increaseArticleStarNum(articleId);
            ipAddress = new IPAddress();
            // 保存点赞信息
            ipAddress.setArticleId(articleId);
            ipAddress.setAddress(ip);
            ipAddress.setCreateTime(new Date());
            articleMapper.insertIpAddress(ipAddress);
            return 1;
        }
        return 0;
    }

    @Override
    public Boolean getIsStarClick(Long articleId, HttpServletRequest request) {
        String ip = IPUtil.getClientIPAddress(request);
        return null != articleMapper.findIpAddressByArticleAndIp(articleId, ip);
    }

}
