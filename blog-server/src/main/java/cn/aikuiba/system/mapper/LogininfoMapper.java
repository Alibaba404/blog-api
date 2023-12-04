package cn.aikuiba.system.mapper;

import cn.aikuiba.system.dto.LoginDto;
import cn.aikuiba.system.entity.Logininfo;
import cn.aikuiba.system.entity.Menu;
import cn.aikuiba.system.query.LogininfoQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/22 10:57
 *
 * @description
 */
@Mapper
public interface LogininfoMapper {

    List<Logininfo> findAll();

    Logininfo findOne(Long id);

    void insert(Logininfo logininfo);

    void delete(Long id);

    void update(Logininfo logininfo);

    void deleteBatch(Long[] ids);

    List<Logininfo> page(LogininfoQuery logininfoQuery);

    Logininfo findByUsername(LoginDto loginDto);

    List<String> findPermissionByLogininfoId(Long logininfoId);

    List<Menu> findMenuByLogininfoId(Long logininfoId);

    void deleteByEmployeeId(Long employeeId);

    void deleteByUserId(Long userId);

    void updatePassword(@Param("id") Long id, @Param("original") String original, @Param("newpass") String newpass);
}
