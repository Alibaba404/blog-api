package cn.aikuiba.system.service;


import cn.aikuiba.system.entity.Employee;
import cn.aikuiba.system.query.EmployeeQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/22 11:07
 *
 * @description
 */
public interface IEmployeeService {

    List<Employee> findAll();

    Employee findOne(Long uid);

    void saveOne(Employee employee);

    void delete(Long uid);

    void update(Employee employee);

    void delBatch(Long[] ids);

    PageInfo<Employee> page(EmployeeQuery employeeQuery);

    Employee findEmployeeByUsername(String username);

    void updatePasswordByLogininfo(Long logininfoId, String original, String newpass);
}
