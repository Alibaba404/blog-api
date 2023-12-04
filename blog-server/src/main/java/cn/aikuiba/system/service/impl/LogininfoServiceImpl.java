package cn.aikuiba.system.service.impl;

import cn.aikuiba.exception.BisException;
import cn.aikuiba.system.dto.LoginDto;
import cn.aikuiba.system.dto.LogininfoDto;
import cn.aikuiba.system.entity.Logininfo;
import cn.aikuiba.system.entity.Menu;
import cn.aikuiba.system.jwt.JwtUtil;
import cn.aikuiba.system.jwt.Payload;
import cn.aikuiba.system.mapper.EmployeeMapper;
import cn.aikuiba.system.mapper.LogininfoMapper;
import cn.aikuiba.system.query.LogininfoQuery;
import cn.aikuiba.system.service.IEmployeeService;
import cn.aikuiba.system.service.ILogininfoService;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
@Service
public class LogininfoServiceImpl implements ILogininfoService {

    @Autowired
    private LogininfoMapper logininfoMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<Logininfo> findAll() {
        return logininfoMapper.findAll();
    }

    @Override
    public Logininfo findOne(Long id) {
        return logininfoMapper.findOne(id);
    }

    @Override
    public void saveOne(Logininfo logininfo) {
        logininfoMapper.insert(logininfo);
    }

    @Override
    public void delete(Long id) {
        logininfoMapper.delete(id);
    }

    @Override
    public void update(Logininfo logininfo) {
        logininfoMapper.update(logininfo);
    }

    @Override
    public void delBatch(Long[] ids) {
        logininfoMapper.deleteBatch(ids);
    }

    @Override
    public PageInfo<Logininfo> page(LogininfoQuery logininfoQuery) {
        PageHelper.startPage(logininfoQuery.getCurrentPage(), logininfoQuery.getPageSize());
        PageHelper.orderBy(logininfoQuery.getOrderBy());
        List<Logininfo> logininfos = logininfoMapper.page(logininfoQuery);
        return new PageInfo<>(logininfos);
    }

    @Override
    public String accountLogin(LoginDto loginDto) {
        String account = loginDto.getAccount();
        String password = loginDto.getPassword();
        Integer type = loginDto.getType();
        // 空值
        if (StrUtil.isBlank(account) || StrUtil.isBlank(password)) {
            throw new BisException("参数不能为空");
        }
        // 账户
        Logininfo logininfo = logininfoMapper.findByUsername(loginDto);
        if (null == logininfo) {
            throw new BisException("账号不存在");
        }
        // 禁用状态
        if (logininfo.getDisable()) {
            throw new BisException("账号已被禁用");
        }
        // 密码
        String salt = logininfo.getSalt();
        String _password = SecureUtil.md5(password + salt);
        if (!_password.equals(logininfo.getPassword())) {
            throw new BisException("密码错误");
        }
        // 处理敏感信息
        logininfo.setSalt("");
        logininfo.setPassword("");
        // 生成登录信息
        Payload payload = new Payload();
        payload.setLogininfo(logininfo);
        if (loginDto.getType() == 0) {
            // 获取管理员全部权限
            List<String> permissions = logininfoMapper.findPermissionByLogininfoId(logininfo.getId());
            // 获取管理员全部菜单
            List<Menu> menus = this.getMenuTree(logininfoMapper.findMenuByLogininfoId(logininfo.getId()));
            // 设置到登录信息中
            payload.setPermissions(permissions);
            payload.setMenus(menus);
        }
        return JwtUtil.generateToken(payload);
    }

    @Override
    public void deleteByEmployeeId(Long employeeId) {
        logininfoMapper.deleteByEmployeeId(employeeId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        logininfoMapper.deleteByUserId(userId);
    }

    @Override
    public void updateLogininfo(LogininfoDto logininfoDto) {
        //登录人id
        Long id = logininfoDto.getId();
        String original = logininfoDto.getOriginal();
        String newpass = logininfoDto.getNewpass();
        String avatarUrl = logininfoDto.getAvatarUrl();
        Logininfo one = this.findOne(id);
        if (null != one) {
            if (StrUtil.isNotBlank(original) && StrUtil.isNotBlank(newpass)) {
                String salt = one.getSalt();
                original = SecureUtil.md5(original + salt);
                newpass = SecureUtil.md5(newpass + salt);
                logininfoMapper.updatePassword(id, original, newpass);
                Integer type = one.getType();
                //修改对应的表
                if (type == 0) { //管理员:tb_employee
                    employeeMapper.updatePasswordByLogininfo(id, original, newpass);
                }
            }


            // 修改头像地址
            if (StrUtil.isNotBlank(avatarUrl)) {
                one.setAvatarUrl(avatarUrl);
                this.update(one);
            }
        }
    }

    /**
     * 获取菜单树
     *
     * @param allMenu
     * @return
     */
    private List<Menu> getMenuTree(List<Menu> allMenu) {
        List<Menu> menuTree = new ArrayList<>();
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
