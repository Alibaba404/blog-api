package cn.aikuiba.system.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @TableName tb_employee    员工实体类
 */
@Data
public class Employee implements Serializable {

    /**
     * 主键
     */
    private Long id;
    /**
     * 账号
     */
    private String username;
    /**
     * 电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 盐值
     */
    private String salt;
    /**
     * 密码
     */
    private String password;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 员工状态 - 1启用，0禁用
     */
    private Integer state;

    /**
     * 所属部门名称
     */
    private String departmentName;
    /**
     * 登录信息Id
     */
    private Long logininfoId;
    /*
     * 角色ID们
     * */
    private List<Role> roles = new ArrayList<>();


}
