package cn.aikuiba.system.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by 蛮小满Sama at 2023/12/4 09:22
 *
 * @description
 */
@Data
public class IPAddress {
    private Long id;
    private Long articleId;
    private String address;
    private Date createTime;
}
