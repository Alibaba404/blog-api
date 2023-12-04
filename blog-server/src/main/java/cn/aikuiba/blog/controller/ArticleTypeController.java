package cn.aikuiba.blog.controller;

import cn.aikuiba.blog.entity.ArticleType;
import cn.aikuiba.blog.query.ArticleTypeQuery;
import cn.aikuiba.blog.service.IArticleTypeService;
import cn.aikuiba.resp.R;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文章类型接口
 * Created by 蛮小满Sama at 2023/11/18 10:35
 *
 * @description
 */
@RestController
@RequestMapping("/articleType")
public class ArticleTypeController {

    @Autowired
    private IArticleTypeService articleTypeService;

    /**
     * 查询全部文章类型信息
     *
     * @return
     */
    @GetMapping
    public R<List<ArticleType>> findAll() {
        return R.success(articleTypeService.findAll());
    }

    /**
     * 根据ID查询文章类型
     *
     * @param id 文章类型ID
     * @return
     */
    @GetMapping("/{id}")
    public R<ArticleType> findOne(@PathVariable("id") Long id) {
        return R.success(articleTypeService.findOne(id));
    }

    /**
     * 新增/修改文章类型信息
     *
     * @param articleType 文章类型信息
     */
    @PutMapping
    public R<String> saveOrUpdate(@RequestBody ArticleType articleType) {
        try {
            String message = "添加成功!";
            if (null == articleType.getId()) {
                articleTypeService.saveOne(articleType);
            } else {
                articleTypeService.update(articleType);
                message = "修改成功!";
            }
            return R.success(200, message);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(1002, "服务器异常", e.getMessage());
        }
    }

    /**
     * 根据ID删除一个文章类型信息
     *
     * @param id 文章类型ID
     */
    @DeleteMapping("/{id}")
    public R<Void> delOne(@PathVariable("id") Long id) {
        articleTypeService.delete(id);
        return R.success();
    }

    /**
     * 批量删除文章类型信息
     *
     * @param ids 文章类型ID数组
     * @return
     */
    @PatchMapping
    public R<Void> delBatch(@RequestBody Long[] ids) {
        articleTypeService.delBatch(ids);
        return R.success();
    }

    /**
     * 分页查询
     *
     * @param articleTypeQuery
     * @return
     */
    @PostMapping
    public R<PageInfo<ArticleType>> page(@RequestBody ArticleTypeQuery articleTypeQuery) {
        return R.success(articleTypeService.page(articleTypeQuery));
    }


    @GetMapping("/allTypes")
    public R<List<ArticleType>> allTypes() {
        return R.success(articleTypeService.allArticleType());
    }

}
