package cn.aikuiba.system.mapper;

import cn.aikuiba.system.entity.Employee;
import cn.aikuiba.system.entity.Role;
import cn.aikuiba.system.query.EmployeeQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/22 10:57
 *
 * @description
 */
@Mapper
public interface EmployeeMapper {

    List<Employee> findAll();

    Employee findOne(Long uid);

    void insert(Employee user);

    void delete(Long uid);

    void update(Employee user);

    void deleteBatch(Long[] ids);

    List<Employee> page(EmployeeQuery userQuery);

    Employee findEmployeeByUsername(String username);

    List<Role> findRolesByEmployeeId(Long employeeId);

    void deleteEmployeeRoleByEmployeeId(Long employeeId);

    void updatePasswordByLogininfo(@Param("logininfoId") Long logininfoId,
                                   @Param("original") String original,
                                   @Param("newpass") String newpass);
}
