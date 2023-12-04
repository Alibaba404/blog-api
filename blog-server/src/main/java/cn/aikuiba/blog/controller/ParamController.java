package cn.aikuiba.blog.controller;

import cn.aikuiba.blog.entity.Param;
import cn.aikuiba.blog.query.ParamQuery;
import cn.aikuiba.blog.service.IParamService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参数接口
 * Created by 蛮小满Sama at 2023/11/18 10:35
 *
 * @description
 */
@RestController
@RequestMapping("/param")
public class ParamController {

    @Autowired
    private IParamService paramService;

    /**
     * 查询全部参数信息
     *
     * @return
     */
    @GetMapping
    public R<List<Param>> findAll() {
        return R.success(paramService.findAll());
    }

    /**
     * 根据ID查询参数
     *
     * @param id 参数ID
     * @return
     */
    @GetMapping("/{id}")
    public R<Param> findOne(@PathVariable("id") Long id) {
        return R.success(paramService.findOne(id));
    }

    /**
     * 新增/修改参数信息
     *
     * @param param 参数信息
     */
    @PutMapping
    public R<String> saveOrUpdate(@RequestBody Param param) {
        try {
            String message = "添加成功!";
            if (null == param.getId()) {
                paramService.saveOne(param);
            } else {
                paramService.update(param);
                message = "修改成功!";
            }
            return R.success(200, message);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(1002, "服务器异常", e.getMessage());
        }
    }

    /**
     * 根据ID删除一个参数信息
     *
     * @param id 参数ID
     */
    @DeleteMapping("/{id}")
    public R<Void> delOne(@PathVariable("id") Long id) {
        paramService.delete(id);
        return R.success();
    }

    /**
     * 批量删除参数信息
     *
     * @param ids 参数ID数组
     * @return
     */
    @PatchMapping
    public R<Void> delBatch(@RequestBody Long[] ids) {
        paramService.delBatch(ids);
        return R.success();
    }

    /**
     * 分页查询
     *
     * @param paramQuery
     * @return
     */
    @PostMapping
    public R<PageInfo<Param>> page(@RequestBody ParamQuery paramQuery) {
        return R.success(paramService.page(paramQuery));
    }

    @GetMapping("/getParams")
    public R<Map<String, Object>> getParams() {
        return R.success(paramService.getParams());
    }

}
