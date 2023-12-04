package cn.aikuiba.system.mapper;

import cn.aikuiba.system.entity.Menu;
import cn.aikuiba.system.query.MenuQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/22 10:57
 *
 * @description
 */
@Mapper
public interface MenuMapper {

    List<Menu> findAll();

    Menu findOne(Long id);

    void insert(Menu menu);

    void delete(Long id);

    void update(Menu menu);

    void deleteBatch(Long[] ids);

    List<Menu> page(MenuQuery menuQuery);

    Long findParentByMenuId(Long menuId);
}
