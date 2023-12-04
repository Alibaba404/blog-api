package cn.aikuiba.system.dto;

import lombok.Data;

/**
 * Created by 蛮小满Sama at 2023/12/3 15:49
 *
 * @description
 */
@Data
public class LogininfoDto {
    private Long id;
    private String original;
    private String newpass;
    // 头像地址
    private String avatarUrl;
}
