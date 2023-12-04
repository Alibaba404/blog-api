package cn.aikuiba.system.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @TableName tb_permission
 */
@Data
public class Permission implements Serializable {

    /**
     * 主键
     */
    private Long id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限接口地址
     */
    private String url;
    /**
     * 权限描述
     */
    private String descs;
    /**
     * 权限编码
     */
    private String sn;
}
