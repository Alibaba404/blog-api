package cn.aikuiba.system.controller;

import cn.aikuiba.resp.R;
import cn.aikuiba.system.entity.Employee;
import cn.aikuiba.system.query.EmployeeQuery;
import cn.aikuiba.system.service.IEmployeeService;
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
 * 员工接口
 * Created by 蛮小满Sama at 2023/11/18 10:35
 *
 * @description
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 查询全部员工信息
     *
     * @return
     */
    @GetMapping
    public R<List<Employee>> findAll() {
        return R.success(employeeService.findAll());
    }

    /**
     * 根据ID查询员工
     *
     * @param uid 员工ID
     * @return
     */
    @GetMapping("/{uid}")
    public R<Employee> findOne(@PathVariable("uid") Long uid) {
        return R.success(employeeService.findOne(uid));
    }

    /**
     * 新增/修改员工信息
     *
     * @param employee 员工信息
     */
    @PutMapping
    public R<String> saveOrUpdate(@RequestBody Employee employee) {
        try {
            String message = "添加成功!";
            if (null == employee.getId()) {
                employeeService.saveOne(employee);
            } else {
                employeeService.update(employee);
                message = "修改成功!";
            }
            return R.success(200, message);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(1002, "服务器异常", e.getMessage());
        }
    }

    /**
     * 根据ID删除一个员工信息
     *
     * @param uid 员工ID
     */
    @DeleteMapping("/{uid}")
    public R<Void> delOne(@PathVariable("uid") Long uid) {
        employeeService.delete(uid);
        return R.success();
    }

    /**
     * 批量删除员工信息
     *
     * @param ids 员工ID数组
     * @return
     */
    @PatchMapping
    public R<Void> delBatch(@RequestBody Long[] ids) {
        employeeService.delBatch(ids);
        return R.success();
    }

    /**
     * 分页查询
     *
     * @param employeeQuery
     * @return
     */
    @PostMapping
    public R<PageInfo<Employee>> page(@RequestBody EmployeeQuery employeeQuery) {
        return R.success(employeeService.page(employeeQuery));
    }

}
