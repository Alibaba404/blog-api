package cn.aikuiba.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志表
 *
 * @TableName tb_logs
 */
@Data
public class Logs implements Serializable {

    /**
     * 主键
     */
    private Long id;
    /**
     * 操作人ID
     */
    private Long userId;
    /**
     * 操作人名称
     */
    private String userName;
    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:dd", timezone = "GMT+8")
    private Date createTime;
    /**
     * 操作的类名
     */
    private String className;
    /**
     * 操作的方法名
     */
    private String methodName;
    /**
     * 方法参数
     */
    private String methodParams;
    /**
     * 返回值
     */
    private String returnValue;
    /**
     * 方法执行耗时, 单位:ms
     */
    private Long costTime;
    /**
     * 操作Ip
     */
    private String ip;

}
