package cn.aikuiba.system.service.impl;

import cn.aikuiba.resp.R;
import cn.aikuiba.system.entity.Menu;
import cn.aikuiba.system.mapper.MenuMapper;
import cn.aikuiba.system.query.MenuQuery;
import cn.aikuiba.system.service.IMenuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 蛮小满Sama at 2023/11/22 14:13
 *
 * @description
 */
@CacheConfig(cacheNames = "Menu")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
@Service
public class MenuServiceImpl implements IMenuService {

    private static final String REDIS_KEY = "'MenuTree'";

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> findAll() {
        return menuMapper.findAll();
    }

    @Override
    public Menu findOne(Long id) {
        return menuMapper.findOne(id);
    }

    @CacheEvict(key = REDIS_KEY)
    @Override
    public void saveOne(Menu menu) {
        menuMapper.insert(menu);
    }

    @CacheEvict(key = REDIS_KEY)
    @Override
    public void delete(Long id) {
        menuMapper.delete(id);
    }

    @CacheEvict(key = REDIS_KEY)
    @Override
    public void update(Menu menu) {
        menuMapper.update(menu);
    }

    @CacheEvict(key = REDIS_KEY)
    @Override
    public void delBatch(Long[] ids) {
        menuMapper.deleteBatch(ids);
    }

    @Override
    public PageInfo<Menu> page(MenuQuery menuQuery) {
        PageHelper.startPage(menuQuery.getCurrentPage(), menuQuery.getPageSize());
        PageHelper.orderBy(menuQuery.getOrderBy());
        List<Menu> menus = menuMapper.page(menuQuery);
        return new PageInfo<>(menus);
    }

    /**
     * 开启SpringCache,存入Redis
     *
     * @return
     */
    @Cacheable(key = REDIS_KEY)
    @Override
    public List<Menu> allMenu() {
        return getAllMenu();
    }

    @Override
    public Long findParentByMenuId(Long menuId) {
        return menuMapper.findParentByMenuId(menuId);
    }

    private List<Menu> getAllMenu() {
        List<Menu> menuTree = new ArrayList<>();
        List<Menu> allMenu = this.findAll();
        if (null != allMenu && allMenu.size() > 0) {
            Map<Long, Menu> map = new HashMap<>();
            for (Menu menu : allMenu) {
                map.put(menu.getId(), menu);
            }
            for (Menu menu : allMenu) {
                // 当前类型的父级id
                Long parentId = menu.getParentId();
                if (null == parentId) { // 没有父级,顶级文章类型
                    menuTree.add(menu);
                } else {// 当前文章类型不是顶级类型
                    // 通过父级id去查父级
                    Menu parentMenu = map.get(parentId);
                    if (null != parentMenu) {
                        parentMenu.getChildren().add(menu);
                    }
                }
            }

        }
        return menuTree;
    }
}
