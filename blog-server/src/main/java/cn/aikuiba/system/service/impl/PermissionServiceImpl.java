package cn.aikuiba.system.service.impl;

import cn.aikuiba.system.entity.Permission;
import cn.aikuiba.system.mapper.PermissionMapper;
import cn.aikuiba.system.query.PermissionQuery;
import cn.aikuiba.system.service.IPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/22 14:13
 *
 * @description
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> findAll() {
        return permissionMapper.findAll();
    }

    @Override
    public Permission findOne(Long id) {
        return permissionMapper.findOne(id);
    }

    @Override
    public void saveOne(Permission permission) {
        permissionMapper.insert(permission);
    }

    @Override
    public void delete(Long id) {
        permissionMapper.delete(id);
    }

    @Override
    public void update(Permission permission) {
        permissionMapper.update(permission);
    }

    @Override
    public void delBatch(Long[] ids) {

    }

    @Override
    public PageInfo<Permission> page(PermissionQuery permissionQuery) {
        PageHelper.startPage(permissionQuery.getCurrentPage(), permissionQuery.getPageSize());
        PageHelper.orderBy(permissionQuery.getOrderBy());
        List<Permission> permissions = permissionMapper.page(permissionQuery);
        return new PageInfo<>(permissions);
    }
}
