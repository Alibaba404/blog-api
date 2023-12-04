package cn.aikuiba.system.service;


import cn.aikuiba.system.entity.Permission;
import cn.aikuiba.system.query.PermissionQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/22 11:07
 *
 * @description
 */
public interface IPermissionService {

    List<Permission> findAll();

    Permission findOne(Long id);

    void saveOne(Permission permission);

    void delete(Long id);

    void update(Permission permission);

    void delBatch(Long[] ids);

    PageInfo<Permission> page(PermissionQuery permissionQuery);

}
