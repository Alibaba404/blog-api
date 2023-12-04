package cn.aikuiba.system.controller;

import cn.aikuiba.resp.R;
import cn.aikuiba.system.auth.PermissionAnno;
import cn.aikuiba.system.entity.Menu;
import cn.aikuiba.system.query.MenuQuery;
import cn.aikuiba.system.service.IMenuService;
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
@RequestMapping("/menu")
public class MenuController {

    private final IMenuService menuService;

    @Autowired
    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 查询全部菜单信息
     *
     * @return
     */
    @PermissionAnno(name = "获取全部菜单", sn = "menu:findAll", descs = "获取全部菜单")
    @GetMapping
    public R<List<Menu>> findAll() {
        return R.success(menuService.findAll());
    }

    /**
     * 根据ID查询菜单
     *
     * @param id 菜单ID
     * @return
     */
    @PermissionAnno(name = "获取单个菜单", sn = "menu:findOne", descs = "根据菜单ID获取菜单信息")
    @GetMapping("/{id}")
    public R<Menu> findOne(@PathVariable("id") Long id) {
        return R.success(menuService.findOne(id));
    }

    /**
     * 新增/修改菜单信息
     *
     * @param menu 菜单信息
     */
    @PermissionAnno(name = "保存菜单", sn = "menu:save", descs = "保存或修改菜单信息")
    @PutMapping
    public R<String> saveOrUpdate(@RequestBody Menu menu) {
        try {
            String message = "添加成功!";
            if (null == menu.getId()) {
                menuService.saveOne(menu);
            } else {
                menuService.update(menu);
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
    @PermissionAnno(name = "删除单个菜单", sn = "menu:delOne", descs = "根据菜单ID删除菜单信息")
    @DeleteMapping("/{id}")
    public R<Void> delOne(@PathVariable("id") Long id) {
        menuService.delete(id);
        return R.success();
    }

    /**
     * 批量删除菜单信息
     *
     * @param ids 菜单ID数组
     * @return
     */
    @PermissionAnno(name = "删除多个菜单", sn = "menu:delBatch", descs = "批量删除")
    @PatchMapping
    public R<Void> delBatch(@RequestBody Long[] ids) {
        menuService.delBatch(ids);
        return R.success();
    }

    /**
     * 分页查询
     *
     * @param menuQuery
     * @return
     */
    @PermissionAnno(name = "分页查询", sn = "menu:page", descs = "分页查询")
    @PostMapping
    public R<PageInfo<Menu>> page(@RequestBody MenuQuery menuQuery) {
        menuQuery.setOrderBy("tm.index");
        return R.success(menuService.page(menuQuery));
    }

    /**
     * 菜单无限级数据
     *
     * @return
     */
    @PermissionAnno(name = "菜单无限级数据", sn = "menu:allMenu", descs = "菜单无限级数据")
    @GetMapping("/allMenu")
    public R<List<Menu>> allMenu() {
        return R.success(menuService.allMenu());
    }
}
