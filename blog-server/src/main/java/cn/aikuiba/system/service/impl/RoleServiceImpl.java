package cn.aikuiba.system.service.impl;

import cn.aikuiba.system.entity.Menu;
import cn.aikuiba.system.entity.Permission;
import cn.aikuiba.system.entity.Role;
import cn.aikuiba.system.mapper.RoleMapper;
import cn.aikuiba.system.query.RoleQuery;
import cn.aikuiba.system.service.IMenuService;
import cn.aikuiba.system.service.IRoleService;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/22 11:08
 *
 * @description
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private IMenuService menuService;

    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }

    @Override
    public Role findOne(Long id) {
        return roleMapper.findOne(id);
    }

    @Transactional
    @Override
    public void saveOne(Role role) {
        String roleName = role.getName();
        if (StrUtil.isNotBlank(roleName)) {
            Role r = this.findRoleByRoleName(roleName);
            if (r != null) throw new RuntimeException("角色已存在!");
            // 保存角色表数据
            roleMapper.insert(role);
            List<Menu> menus = role.getMenus();
            if (null != menus && menus.size() > 0) {
                // 保存角色与菜单的关联数据
                roleMapper.insertRoleMenu(role);
            }

            List<Permission> permissions = role.getPermissions();
            if (null != permissions && permissions.size() > 0) {
                // 保存角色与权限的关联数据
                roleMapper.insertRolePermission(role);
            }
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        //删除角色与权限的关联表数据
        roleMapper.deleteRolePermission(id);
        //删除角色与菜单的关联表数据
        roleMapper.deleteRoleMenu(id);
        // 删除角色信息
        roleMapper.delete(id);
    }

    @Transactional
    @Override
    public void update(Role role) {
        roleMapper.update(role);
        //删除角色与权限的关联表数据
        roleMapper.deleteRolePermission(role.getId());
        //删除角色与菜单的关联表数据
        roleMapper.deleteRoleMenu(role.getId());
        List<Menu> menus = role.getMenus();
        if (null != menus && menus.size() > 0) {
            // 保存角色与菜单的关联数据
            roleMapper.insertRoleMenu(role);
            for (Menu menu : menus) {
                Long parentId = menu.getParentId();
                Long roleId = role.getId();
                Integer count = roleMapper.findRoleMenuByMenuParentIdAndRoleId(roleId, parentId);
                if (count == 0) {
                    roleMapper.insertOneRoleMenu(roleId, parentId);
                }
            }
        }
        List<Permission> permissions = role.getPermissions();
        if (null != permissions && permissions.size() > 0) {
            // 保存角色与权限的关联数据
            roleMapper.insertRolePermission(role);
        }
    }

    @Override
    public void delBatch(Long[] ids) {
        roleMapper.deleteBatch(ids);
    }

    @Override
    public PageInfo<Role> page(RoleQuery roleQuery) {
        PageHelper.startPage(roleQuery.getCurrentPage(), roleQuery.getPageSize());
        List<Role> roles = roleMapper.page(roleQuery);
        roles.forEach(role -> {
            role.setMenus(roleMapper.findMenusByRoleId(role.getId()));
            role.setPermissions(roleMapper.findPermissionsByRoleId(role.getId()));
        });
        return new PageInfo<>(roles);
    }

    @Override
    public Role findRoleByRoleName(String roleName) {
        return roleMapper.findRoleByRoleName(roleName);
    }
}
