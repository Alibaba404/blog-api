package cn.aikuiba.user.service.impl;

import cn.aikuiba.system.entity.Logininfo;
import cn.aikuiba.system.service.ILogininfoService;
import cn.aikuiba.user.entity.User;
import cn.aikuiba.user.mapper.UserMapper;
import cn.aikuiba.user.query.UserQuery;
import cn.aikuiba.user.service.IUserService;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/18 10:34
 *
 * @description
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ILogininfoService logininfoService;


    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public User findOne(Long uid) {
        return userMapper.findOne(uid);
    }

    @Transactional
    @Override
    public void saveOne(User user) {
        User one = this.findOneByUserName(user.getUserName());
        if (null != one) throw new RuntimeException("用户名已存在!");
        // 前端密码
        String password = user.getPassword();
        // 颜值
        String salt = RandomUtil.randomString(32);
        // 加密后密文
        String _password = SecureUtil.md5(password + salt);
        user.setSalt(salt);
        user.setPassword(_password);

        //添加登录信息表数据
        Logininfo logininfo = new Logininfo();
        logininfo.setUsername(user.getUserName());
        logininfo.setPhone(user.getPhone());
        logininfo.setEmail(user.getEmail());
        logininfo.setSalt(salt);
        logininfo.setPassword(_password);
        logininfo.setType(1);
        logininfo.setDisable(false);
        logininfoService.saveOne(logininfo);

        user.setLogininfoId(logininfo.getId());
        userMapper.insert(user);

    }

    @Transactional
    @Override
    public void delete(Long uid) {
        // 根据用户ID删除登录信息表关联数据
        logininfoService.deleteByUserId(uid);
        userMapper.delete(uid);
    }

    @Transactional
    @Override
    public void update(User user) {
        // 密码的修改是单独的接口
        user.setPassword(null);
        userMapper.update(user);
    }

    @Override
    public void delBatch(Long[] ids) {
        userMapper.deleteBatch(ids);
    }

    @Override
    public PageInfo<User> page(UserQuery userQuery) {
        // 当前页码
        Integer currentPage = userQuery.getCurrentPage();
        // 分页条数
        Integer pageSize = userQuery.getPageSize();
        // 开启分页,必须在业务分页查询方法的前面,Mybatis才会拦截
        PageHelper.startPage(currentPage, pageSize);
        return new PageInfo<>(userMapper.page(userQuery));
    }

    @Override
    public User findOneByUserName(String userName) {
        return userMapper.findOneByUserName(userName);
    }
}
