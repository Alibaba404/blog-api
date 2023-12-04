package cn.aikuiba.system.controller;

import cn.aikuiba.resp.R;
import cn.aikuiba.system.auth.PermissionAnno;
import cn.aikuiba.system.entity.Role;
import cn.aikuiba.system.query.RoleQuery;
import cn.aikuiba.system.service.IRoleService;
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
 * 角色接口
 * Created by 蛮小满Sama at 2023/11/18 10:35
 *
 * @description 角色管理的接口
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    /**
     * 查询全部角色信息
     *
     * @return
     */
    @PermissionAnno(name = "获取全部角色", sn = "role:finaAll", descs = "获取全部角色")
    @GetMapping
    public R<List<Role>> findAll() {
        return R.success(roleService.findAll());
    }

    /**
     * 根据ID查询角色
     *
     * @param id 角色ID
     * @return
     */
    @PermissionAnno(name = "获取单个角色", sn = "role:findOne", descs = "根据ID获取角色")
    @GetMapping("/{id}")
    public R<Role> findOne(@PathVariable("id") Long id) {
        return R.success(roleService.findOne(id));
    }

    /**
     * 新增/修改角色信息
     *
     * @param role 角色信息
     */
    @PermissionAnno(name = "保存角色", sn = "role:save", descs = "保存或更新角色")
    @PutMapping
    public R<String> saveOrUpdate(@RequestBody Role role) {
        try {
            String message = "添加成功!";
            if (null == role.getId()) {
                roleService.saveOne(role);
            } else {
                roleService.update(role);
                message = "修改成功!";
            }
            return R.success(200, message);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(1002, "服务器异常", e.getMessage());
        }
    }

    /**
     * 根据ID删除一个角色信息
     *
     * @param id 角色ID
     */
    @PermissionAnno(name = "删除单个角色", sn = "role:delOne", descs = "根据角色ID删除角色")
    @DeleteMapping("/{id}")
    public R<Void> delOne(@PathVariable("id") Long id) {
        roleService.delete(id);
        return R.success();
    }

    /**
     * 批量删除角色信息
     *
     * @param ids 角色ID数组
     * @return
     */
    @PermissionAnno(name = "删除多个角色", sn = "role:delBatch", descs = "批量删除角色")
    @PatchMapping
    public R<Void> delBatch(@RequestBody Long[] ids) {
        roleService.delBatch(ids);
        return R.success();
    }

    /**
     * 分页查询
     *
     * @param roleQuery
     * @return
     */
    @PermissionAnno(name = "分页查询角色信息", sn = "role:page", descs = "分页查询角色信息")
    @PostMapping
    public R<PageInfo<Role>> page(@RequestBody RoleQuery roleQuery) {
        return R.success(roleService.page(roleQuery));
    }

}
