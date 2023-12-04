package cn.aikuiba.blog.controller;

import cn.aikuiba.blog.dto.ArticleDto;
import cn.aikuiba.blog.entity.Article;
import cn.aikuiba.blog.entity.ArticleComment;
import cn.aikuiba.blog.query.ArticleCommentQuery;
import cn.aikuiba.blog.query.ArticleQuery;
import cn.aikuiba.blog.service.IArticleCommentService;
import cn.aikuiba.blog.service.IArticleService;
import cn.aikuiba.resp.R;
import cn.aikuiba.resp.RCode;
import cn.aikuiba.system.aop.LogsAnno;
import cn.aikuiba.utils.BaiduAuditUtils;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 文章接口
 * Created by 蛮小满Sama at 2023/11/18 10:35
 *
 * @description
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IArticleCommentService articleCommentService;

    /**
     * 查询全部文章信息
     *
     * @return
     */
    @GetMapping
    public R<List<Article>> findAll() {
        return R.success(articleService.findAll());
    }

    /**
     * 根据ID查询文章
     *
     * @param id 文章ID
     * @return
     */
    @GetMapping("/{id}")
    public R<Article> findOne(@PathVariable("id") Long id, HttpServletRequest request) {
        return R.success(articleService.findOne(id, request));
    }

    /**
     * 新增/修改文章信息
     *
     * @param article 文章信息
     */
    @LogsAnno
    @PutMapping
    public R<String> saveOrUpdate(@RequestBody Article article) {
        try {
            String message = "添加成功!";
            if (null == article.getId()) {
                articleService.saveOne(article);
            } else {
                articleService.update(article);
                message = "修改成功!";
            }
            return R.success(200, message);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(1002, "服务器异常", e.getMessage());
        }
    }

    /**
     * 根据ID删除一个文章信息
     *
     * @param id 文章ID
     */
    @LogsAnno
    @DeleteMapping("/{id}")
    public R<Void> delOne(@PathVariable("id") Long id) {
        articleService.delete(id);
        return R.success();
    }

    /**
     * 批量删除文章信息
     *
     * @param ids 文章ID数组
     * @return
     */
    @LogsAnno
    @PatchMapping
    public R<Void> delBatch(@RequestBody Long[] ids) {
        articleService.delBatch(ids);
        return R.success();
    }

    /**
     * 分页查询
     *
     * @param articleQuery
     * @return
     */
    @PostMapping
    public R<PageInfo<Article>> page(@RequestBody ArticleQuery articleQuery) {
        articleQuery.setOrderBy("ta.top_state DESC,ta.create_time DESC");
        return R.success(articleService.page(articleQuery));
    }

    @GetMapping("/countArticleType")
    public R<List<ArticleDto>> getCountArticleType() {
        return R.success(articleService.getCountArticleType());
    }

    @GetMapping("/countArticle")
    public R<List<ArticleDto>> getCountArticle() {
        return R.success(articleService.getCountArticle());
    }

    @GetMapping("/getRelatedArticles/{articleId}")
    public R<List<Article>> getRelatedArticles(@PathVariable("articleId") Long articleId) {
        return R.success(articleService.getRelatedArticles(articleId));
    }

    @GetMapping("/getArticleByArticleType/{articleTypeId}")
    public R<List<Article>> getArticleByArticleType(@PathVariable("articleTypeId") Long articleTypeId) {
        return R.success(articleService.getArticleByArticleType(articleTypeId));
    }

    /**
     * 新增文章评论
     *
     * @param articleComment 文章评论实体
     * @return
     */
    @PutMapping("/addNewArticleComment")
    public R<Void> addNewArticleComment(@RequestBody ArticleComment articleComment) {
        String content = articleComment.getContent();
        if (StrUtil.isNotBlank(content)) {
            if (!BaiduAuditUtils.textCensor(content)) {
                return R.failure(RCode.APP_ARTICLE_COMMENT_NON_COMPLIANCE.code(), RCode.APP_ARTICLE_COMMENT_NON_COMPLIANCE.message());
            }
        }
        articleCommentService.saveOne(articleComment);
        return R.success();
    }

    /**
     * 查询文章所有评论
     *
     * @param articleId 文章Id
     * @return
     */
    @GetMapping("/getArticleComments/{articleId}")
    public R<List<ArticleComment>> getArticleComments(@PathVariable("articleId") Long articleId) {
        return R.success(articleCommentService.findArticleCommentByArticleId(articleId));
    }

    @PostMapping(value = "/getArticleCommentPage")
    public R<Page<ArticleComment>> getArticleCommentPage(@RequestBody ArticleCommentQuery articleCommentQuery) {
        return R.success(articleCommentService.getArticleCommentPage(articleCommentQuery));
    }


    /**
     * 删除文章评论
     *
     * @param articleCommentId 文章评论id
     * @return
     */
    @DeleteMapping("/delArticleComment/{articleCommentId}")
    public R<Void> delArticleComment(@PathVariable("articleCommentId") String articleCommentId) {
        articleCommentService.delete(articleCommentId);
        return R.success();
    }

    /**
     * 获取文章归档
     *
     * @param time 查询日期:all 表示全部
     * @return
     */
    @GetMapping({"/getArticleArchivist", "/getArticleArchivist/{time}"})
    public R<List<Article>> getArticleArchivist(@PathVariable(value = "time", required = false) String time) {
        return R.success(articleService.getArticleArchivist(time));
    }

    /**
     * 获取点赞前6的点赞数据
     *
     * @return
     */
    @GetMapping("/topStarNum")
    public R<Map<String, Object>> getTopStarNum() {
        return R.success(articleService.getTopStarNum());
    }

    /**
     * 获取文章数据前5的文章
     *
     * @return
     */
    @GetMapping("/topCountArticleType")
    public R<List<ArticleDto>> getTopCountArticleType() {
        return R.success(articleService.getTopCountArticleType(5));
    }

    @PostMapping("/addArticleReadCount/{articleId}")
    public R<Void> addArticleReadCount(@PathVariable("articleId") Long articleId) {
        articleService.addArticleReadCount(articleId);
        return R.success();
    }

    /**
     * 点赞
     *
     * @param articleId 用户ID
     * @param request
     * @return
     */
    @PostMapping("/addArticleStarNum/{articleId}")
    public R<Integer> addArticleStarNum(@PathVariable("articleId") Long articleId, HttpServletRequest request) {
        return R.success(articleService.addArticleStarNum(articleId, request));
    }
    @PostMapping("/subArticleStarNum/{articleId}")
    public R<Integer> subArticleStarNum(@PathVariable("articleId") Long articleId, HttpServletRequest request) {
        return R.success(articleService.subArticleStarNum(articleId, request));
    }

    /**
     * 当前用户是否已点赞
     *
     * @param articleId
     * @param request
     * @return
     */
    @GetMapping("/isStarClick/{articleId}")
    public R<Boolean> getIsStarClick(@PathVariable("articleId") Long articleId, HttpServletRequest request) {
        return R.success(articleService.getIsStarClick(articleId, request));
    }
}
