package cn.aikuiba.system.service;


import cn.aikuiba.system.entity.Role;
import cn.aikuiba.system.query.RoleQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/22 11:07
 *
 * @description
 */
public interface IRoleService {

    List<Role> findAll();

    Role findOne(Long id);

    void saveOne(Role role);

    void delete(Long id);

    void update(Role role);

    void delBatch(Long[] ids);

    PageInfo<Role> page(RoleQuery roleQuery);

    Role findRoleByRoleName(String roleName);
}
