package cn.aikuiba.blog.service.impl;

import cn.aikuiba.blog.controller.PageBean;
import cn.aikuiba.blog.entity.Article;
import cn.aikuiba.blog.entity.ArticleComment;
import cn.aikuiba.blog.query.ArticleCommentQuery;
import cn.aikuiba.blog.repository.ICommentRepository;
import cn.aikuiba.blog.service.IArticleCommentService;
import cn.aikuiba.blog.service.IArticleService;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by 蛮小满Sama at 2023/11/25 10:14
 *
 * @description
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
@Service
public class ArticleCommentServiceImpl implements IArticleCommentService {

    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private IArticleService articleService;

    @Transactional
    @Override
    public void saveOne(ArticleComment articleComment) {
        articleComment.setState(1);
        commentRepository.save(articleComment);
        //更新文章评论数量
        Long articleId = articleComment.getArticleId();
        articleService.addCommentCount(articleId);
    }

    /**
     * 删除指定评论
     *
     * @param id Mongodb数据库主键id
     */
    @Transactional
    @Override
    public void delete(String id) {
        Optional<ArticleComment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            Long articleId = comment.get().getArticleId();
            articleService.subtractCommentCount(articleId);
        }

        commentRepository.deleteById(id);
    }

    @Override
    public void update(ArticleComment articleComment) {

    }

    /**
     * 根据文章Id查询所有的评论(未分页)
     * findArticleCommentBy:固定写法
     * By后面的必须是实体类里面的字段(属性),有代码提示
     *
     * @param articleId
     * @return
     * @articleComment articleId 文章Id
     */
    @Override
    public List<ArticleComment> findArticleCommentByArticleId(Long articleId) {
        return commentRepository.findArticleCommentByArticleId(articleId)
                .stream()
                .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                .toList();
    }


    @Override
    public Page<ArticleComment> getArticleCommentPage(ArticleCommentQuery articleCommentQuery) {
        Integer currentPage = articleCommentQuery.getCurrentPage();
        Integer pageSize = articleCommentQuery.getPageSize();
        //查询条件,可以传递多个查询条件
        ArticleComment articleComment = new ArticleComment();
        articleComment.setArticleId(articleCommentQuery.getArticleId());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("createTime", "nickname", "content", "userId", "likenum", "replynum", "parentId", "state")
                .withIgnoreCase(true)
                .withMatcher("articleId", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<ArticleComment> example = Example.of(articleComment, matcher);
        //分页条件
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return commentRepository.findAll(example, pageable);
    }
}
