package cn.aikuiba.system.mapper;

import cn.aikuiba.system.entity.Permission;
import cn.aikuiba.system.query.MenuQuery;
import cn.aikuiba.system.query.PermissionQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/22 10:57
 *
 * @description
 */
@Mapper
public interface PermissionMapper {

    List<Permission> findAll();

    Permission findOne(Long id);

    void insert(Permission permission);

    void delete(Long id);

    void update(Permission permission);

    void deleteBatch(Long[] ids);

    List<Permission> page(PermissionQuery permissionQuery);

    Permission findBySn(String sn);
}
