package cn.aikuiba.user.mapper;

import cn.aikuiba.user.entity.User;
import cn.aikuiba.user.query.UserQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/18 10:24
 *
 * @description
 */
@Mapper
public interface UserMapper {

    List<User> findAll();

    User findOne(Long uid);

    void insert(User user);

    void delete(Long uid);

    void update(User user);

    void deleteBatch(Long[] ids);

    List<User> page(UserQuery userQuery);

    User findOneByUserName(String userName);
}
