package cn.aikuiba.user.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息表
 *
 * @TableName tb_user
 */
@Data
public class User implements Serializable {

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 用户账号
     */
    private String userName;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 用户邮箱
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
     * 头像地址
     */
    private String avatar;
    /**
     * 备注
     */
    private String remark;
    /**
     * 登录信息Id
     */
    private Long logininfoId;

}
