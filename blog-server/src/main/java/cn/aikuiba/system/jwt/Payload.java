package cn.aikuiba.system.jwt;

import cn.aikuiba.system.entity.Logininfo;
import cn.aikuiba.system.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/28 14:38
 *
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payload {

    // 1.登录基本信息
    private Logininfo logininfo;

    // 2.登录资源权限信息，前端只需要权限的sn编码
    private List<String> permissions;

    // 3.登录菜单权限信息
    private List<Menu> menus;
}
