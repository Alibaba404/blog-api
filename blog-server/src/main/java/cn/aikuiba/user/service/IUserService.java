package cn.aikuiba.user.service;

import cn.aikuiba.user.entity.User;
import cn.aikuiba.user.query.UserQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/18 10:33
 *
 * @description
 */
public interface IUserService {

    List<User> findAll();

    User findOne(Long uid);

    void saveOne(User user);

    void delete(Long uid);

    void update(User user);

    void delBatch(Long[] ids);

    PageInfo<User> page(UserQuery userQuery);

    /**
     * 根据用户名查询用户
     *
     * @param userName 用户名
     * @return
     */
    User findOneByUserName(String userName);
}
