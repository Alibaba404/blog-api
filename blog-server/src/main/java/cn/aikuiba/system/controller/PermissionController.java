package cn.aikuiba.system.controller;

import cn.aikuiba.resp.R;
import cn.aikuiba.system.auth.PermissionAnno;
import cn.aikuiba.system.entity.Permission;
import cn.aikuiba.system.query.PermissionQuery;
import cn.aikuiba.system.service.IPermissionService;
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
@RequestMapping("/permission")
public class PermissionController {

    private final IPermissionService permissionService;

    @Autowired
    public PermissionController(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * 查询全部菜单信息
     *
     * @return
     */
    @PermissionAnno(name = "查询全部权限", sn = "permission:findAll", descs = "查询全部权限")
    @GetMapping
    public R<List<Permission>> findAll() {
        return R.success(permissionService.findAll());
    }

    /**
     * 根据ID查询菜单
     *
     * @param id 菜单ID
     * @return
     */
    @PermissionAnno(name = "查询单个权限", sn = "permission:findOne", descs = "通过权限ID查询")
    @GetMapping("/{id}")
    public R<Permission> findOne(@PathVariable("id") Long id) {
        return R.success(permissionService.findOne(id));
    }

    /**
     * 新增/修改菜单信息
     *
     * @param permission 菜单信息
     */
    @PermissionAnno(name = "保存权限", sn = "permission:save", descs = "新增或修改权限")
    @PutMapping
    public R<String> saveOrUpdate(@RequestBody Permission permission) {
        try {
            String message = "添加成功!";
            if (null == permission.getId()) {
                permissionService.saveOne(permission);
            } else {
                permissionService.update(permission);
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
    @PermissionAnno(name = "删除单个权限", sn = "permission:delOne", descs = "根据ID删除权限")
    @DeleteMapping("/{id}")
    public R<Void> delOne(@PathVariable("id") Long id) {
        permissionService.delete(id);
        return R.success();
    }

    /**
     * 批量删除菜单信息
     *
     * @param ids 菜单ID数组
     * @return
     */
    @PermissionAnno(name = "删除多个权限", sn = "permission:delBatch", descs = "根据权限ID数组删除权限")
    @PatchMapping
    public R<Void> delBatch(@RequestBody Long[] ids) {
        permissionService.delBatch(ids);
        return R.success();
    }

    /**
     * 分页查询
     *
     * @param permissionQuery
     * @return
     */
    @PermissionAnno(name = "分页查询权限", sn = "permission:page", descs = "分页查询高级查询权限")
    @PostMapping
    public R<PageInfo<Permission>> page(@RequestBody PermissionQuery permissionQuery) {
        return R.success(permissionService.page(permissionQuery));
    }

}
