package cn.aikuiba.system.dto;

import lombok.Data;

/**
 * Created by 蛮小满Sama at 2023/11/28 15:34
 *
 * @description
 */
@Data
public class LoginDto {

    private String account;

    private String password;

    private Integer type;
}
