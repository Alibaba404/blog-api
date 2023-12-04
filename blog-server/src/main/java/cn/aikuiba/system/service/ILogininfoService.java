package cn.aikuiba.system.service;


import cn.aikuiba.system.dto.LoginDto;
import cn.aikuiba.system.dto.LogininfoDto;
import cn.aikuiba.system.entity.Logininfo;
import cn.aikuiba.system.query.LogininfoQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/22 11:07
 *
 * @description
 */
public interface ILogininfoService {

    List<Logininfo> findAll();

    Logininfo findOne(Long id);

    void saveOne(Logininfo logininfo);

    void delete(Long id);

    void update(Logininfo logininfo);

    void delBatch(Long[] ids);

    PageInfo<Logininfo> page(LogininfoQuery logininfoQuery);

    /**
     * 账号密码登录
     *
     * @param loginDto 登录信息dto
     * @return
     */
    String accountLogin(LoginDto loginDto);

    /**
     * 通过员工ID删除
     *
     * @param employeeId 员工ID
     */
    void deleteByEmployeeId(Long employeeId);

    void deleteByUserId(Long userId);

    void updateLogininfo(LogininfoDto logininfoDto);
}
