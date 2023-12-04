package cn.aikuiba.system.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/27 09:18
 *
 * @description 角色实体类
 */
@Data
public class Role {
    private Long id;
    private String name;
    private String sn;
    private List<Menu> menus;
    private List<Permission> permissions;
}
