package cn.aikuiba.user.controller;

import cn.aikuiba.resp.R;
import cn.aikuiba.user.entity.User;
import cn.aikuiba.user.query.UserQuery;
import cn.aikuiba.user.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户接口
 * Created by 蛮小满Sama at 2023/11/18 10:35
 *
 * @description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 查询全部用户信息
     *
     * @return
     */
    @GetMapping
    public R<List<User>> findAll() {
        return R.success(userService.findAll());
    }

    /**
     * 根据ID查询用户
     *
     * @param uid 用户ID
     * @return
     */
    @GetMapping("/{uid}")
    public R<User> findOne(@PathVariable("uid") Long uid) {
        return R.success(userService.findOne(uid));
    }

    /**
     * 新增/修改用户信息
     *
     * @param user 用户信息
     */
    @PutMapping
    public R<String> saveOrUpdate(@RequestBody User user) {
        try {
            String message = "添加成功!";
            if (null == user.getId()) {
                userService.saveOne(user);
            } else {
                userService.update(user);
                message = "修改成功!";
            }
            return R.success(200, message);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(1002, "服务器异常", e.getMessage());
        }
    }

    /**
     * 根据ID删除一个用户信息
     *
     * @param uid 用户ID
     */
    @DeleteMapping("/{uid}")
    public R<Void> delOne(@PathVariable("uid") Long uid) {
        userService.delete(uid);
        return R.success();
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 用户ID数组
     * @return
     */
    @PatchMapping
    public R<Void> delBatch(@RequestBody Long[] ids) {
        userService.delBatch(ids);
        return R.success();
    }

    /**
     * 分页查询
     *
     * @param userQuery
     * @return
     */
    @PostMapping
    public R<PageInfo<User>> page(@RequestBody UserQuery userQuery) {
        return R.success(userService.page(userQuery));
    }

}
