package cn.aikuiba.system.service.impl;

import cn.aikuiba.system.entity.Employee;
import cn.aikuiba.system.entity.Logininfo;
import cn.aikuiba.system.entity.Role;
import cn.aikuiba.system.mapper.EmployeeMapper;
import cn.aikuiba.system.mapper.RoleMapper;
import cn.aikuiba.system.query.EmployeeQuery;
import cn.aikuiba.system.service.IEmployeeService;
import cn.aikuiba.system.service.ILogininfoService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/22 11:08
 *
 * @description
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ILogininfoService logininfoService;

    @Override
    public List<Employee> findAll() {
        return employeeMapper.findAll();
    }

    @Override
    public Employee findOne(Long uid) {
        return employeeMapper.findOne(uid);
    }

    @Transactional
    @Override
    public void saveOne(Employee employee) {
        String username = employee.getUsername();
        if (StrUtil.isNotBlank(username)) {
            Employee e = this.findEmployeeByUsername(username);
            if (e != null) throw new RuntimeException("账号已存在!");
            String password = employee.getPassword();
            String salt = RandomUtil.randomString(32);
            if (StrUtil.isNotBlank(password)) {
                employee.setPassword(SecureUtil.md5(password + salt));
                employee.setSalt(salt);
            }
            Logininfo logininfo = new Logininfo();
            // 属性复制
            BeanUtil.copyProperties(employee, logininfo);
            logininfo.setType(0);
            logininfo.setDisable(false);
            // 新增登录信息表数据
            logininfoService.saveOne(logininfo);
            employee.setLogininfoId(logininfo.getId());
            // 新增员工数据
            employeeMapper.insert(employee);
            // 新增后的员工id
            Long employeeId = employee.getId();
            // 前端添加的角色列表
            List<Role> roles = employee.getRoles();
            if (null != roles && roles.size() > 0) {
                // 保存员工-角色中间表数据
                roleMapper.insertEmployeeRole(roles, employeeId);
            }
        }
    }

    @Transactional
    @Override
    public void delete(Long uid) {
        // 删除员工与角色的关联表数据
        employeeMapper.deleteEmployeeRoleByEmployeeId(uid);
        // 删除登录信息表关联数据
        logininfoService.deleteByEmployeeId(uid);
        // 删除员工信息
        employeeMapper.delete(uid);
    }

    @Transactional
    @Override
    public void update(Employee employee) {
        Long employeeId = employee.getId();
        // 清空已关联的员工角色关系映射
        employeeMapper.deleteEmployeeRoleByEmployeeId(employeeId);
        // 更新员工信息
        employeeMapper.update(employee);
        // 新增员工角色关系映射
        List<Role> roles = employee.getRoles();
        if (null != roles && roles.size() > 0) {
            // 保存员工-角色中间表数据
            roleMapper.insertEmployeeRole(roles, employeeId);
        }
    }

    @Transactional
    @Override
    public void delBatch(Long[] ids) {
        employeeMapper.deleteBatch(ids);
    }

    @Override
    public PageInfo<Employee> page(EmployeeQuery employeeQuery) {
        PageHelper.startPage(employeeQuery.getCurrentPage(), employeeQuery.getPageSize());
        List<Employee> employees = employeeMapper.page(employeeQuery);
        employees.forEach(emp -> {
            emp.setRoles(employeeMapper.findRolesByEmployeeId(emp.getId()));
        });
        return new PageInfo<>(employees);
    }

    @Override
    public Employee findEmployeeByUsername(String username) {
        return employeeMapper.findEmployeeByUsername(username);
    }

    @Override
    public void updatePasswordByLogininfo(Long logininfoId, String original, String newpass) {
        employeeMapper.updatePasswordByLogininfo(logininfoId, original, newpass);
    }
}
