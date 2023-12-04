package cn.aikuiba.system.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @TableName tb_menu   菜单实体类
 */
@Data
public class Menu implements Serializable {

    /**
     * 主键
     */
    private Long id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * Vue组件路径
     */
    private String component;
    /**
     * 菜单路由地址
     */
    private String url;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 菜单排序
     */
    private Integer index;
    /**
     * 上级菜单Id
     */
    private Long parentId;

    /**
     * 上级菜单名称
     */
    private String parentName;

    /**
     * 排除
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Menu> children = new ArrayList<>();

}
