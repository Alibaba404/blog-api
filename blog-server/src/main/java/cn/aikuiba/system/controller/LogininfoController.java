package cn.aikuiba.system.controller;

import cn.aikuiba.resp.R;
import cn.aikuiba.system.dto.LogininfoDto;
import cn.aikuiba.system.entity.Logininfo;
import cn.aikuiba.system.query.LogininfoQuery;
import cn.aikuiba.system.service.ILogininfoService;
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

import java.util.List;

/**
 * 菜单接口
 * Created by 蛮小满Sama at 2023/11/18 10:35
 *
 * @description
 */
@RestController
@RequestMapping("/logininfo")
public class LogininfoController {

    private final ILogininfoService logininfoService;

    @Autowired
    public LogininfoController(ILogininfoService logininfoService) {
        this.logininfoService = logininfoService;
    }

    /**
     * 查询全部菜单信息
     *
     * @return
     */
    @GetMapping
    public R<List<Logininfo>> findAll() {
        return R.success(logininfoService.findAll());
    }

    /**
     * 根据ID查询菜单
     *
     * @param id 菜单ID
     * @return
     */
    @GetMapping("/{id}")
    public R<Logininfo> findOne(@PathVariable("id") Long id) {
        return R.success(logininfoService.findOne(id));
    }

    /**
     * 新增/修改菜单信息
     *
     * @param logininfo 菜单信息
     */
    @PutMapping
    public R<String> saveOrUpdate(@RequestBody Logininfo logininfo) {
        try {
            String message = "添加成功!";
            if (null == logininfo.getId()) {
                logininfoService.saveOne(logininfo);
            } else {
                logininfoService.update(logininfo);
                message = "修改成功!";
            }
            return R.success(200, message);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(1002, "服务器异常", e.getMessage());
        }
    }

    /**
     * 根据ID删除一个菜单信息
     *
     * @param id 菜单ID
     */
    @DeleteMapping("/{id}")
    public R<Void> delOne(@PathVariable("id") Long id) {
        logininfoService.delete(id);
        return R.success();
    }

    /**
     * 批量删除菜单信息
     *
     * @param ids 菜单ID数组
     * @return
     */
    @PatchMapping
    public R<Void> delBatch(@RequestBody Long[] ids) {
        logininfoService.delBatch(ids);
        return R.success();
    }

    /**
     * 分页查询
     *
     * @param logininfoQuery
     * @return
     */
    @PostMapping
    public R<PageInfo<Logininfo>> page(@RequestBody LogininfoQuery logininfoQuery) {
        logininfoQuery.setOrderBy("tm.index");
        return R.success(logininfoService.page(logininfoQuery));
    }

    /**
     * 修改登录信息
     *
     * @return
     */
    @PostMapping("/updateLogininfo")
    public R<Void> updateLogininfo(@RequestBody LogininfoDto logininfoDto) {
        logininfoService.updateLogininfo(logininfoDto);
        return R.success();
    }

}
