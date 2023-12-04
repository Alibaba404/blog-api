package cn.aikuiba.system.service;


import cn.aikuiba.system.entity.Menu;
import cn.aikuiba.system.query.MenuQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/22 11:07
 *
 * @description
 */
public interface IMenuService {

    List<Menu> findAll();

    Menu findOne(Long id);

    void saveOne(Menu menu);

    void delete(Long id);

    void update(Menu menu);

    void delBatch(Long[] ids);

    PageInfo<Menu> page(MenuQuery menuQuery);

    List<Menu> allMenu();

    /**
     * 获取当前才的父级id
     *
     * @param menuId
     * @return
     */
    Long findParentByMenuId(Long menuId);
}
