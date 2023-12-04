package cn.aikuiba.system.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @TableName tb_logininfo
 */
@Data
public class Logininfo implements Serializable {

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String username;
    /**
     *
     */
    private String phone;
    /**
     * 电话号码
     */
    private String email;
    /**
     * 邮箱
     */
    private String salt;
    /**
     * 盐值
     */
    private String password;
    /**
     * 类型 - 0管理员，1用户
     */
    private Integer type;
    /**
     * 启用状态：true可用，false禁用
     */
    private Boolean disable;

    /**
     * 头像地址
     */
    private String avatarUrl;


}
