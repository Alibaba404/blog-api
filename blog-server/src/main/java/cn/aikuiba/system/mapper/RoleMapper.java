package cn.aikuiba.system.mapper;

import cn.aikuiba.system.entity.Menu;
import cn.aikuiba.system.entity.Permission;
import cn.aikuiba.system.entity.Role;
import cn.aikuiba.system.query.RoleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/22 10:57
 *
 * @description
 */
@Mapper
public interface RoleMapper {

    List<Role> findAll();

    Role findOne(Long id);

    void insert(Role role);

    void delete(Long id);

    void update(Role role);

    void deleteBatch(Long[] ids);

    List<Role> page(RoleQuery roleQuery);

    /**
     * 员工-角色中间表数据
     *
     * @param roles      角色们
     * @param employeeId 员工id
     */
    void insertEmployeeRole(@Param("roles") List<Role> roles, @Param("employeeId") Long employeeId);

    void insertRoleMenu(Role role);

    Role findRoleByRoleName(String roleName);

    List<Menu> findMenusByRoleId(Long roleId);

    List<Permission> findPermissionsByRoleId(Long roleId);

    void insertRolePermission(Role role);

    void deleteRolePermission(Long roleId);

    void deleteRoleMenu(Long roleId);

    Integer findRoleMenuByMenuParentIdAndRoleId(@Param("roleId") Long roleId, @Param("parentId") Long parentId);

    void insertOneRoleMenu(@Param("roleId") Long roleId, @Param("parentId") Long parentId);
}
