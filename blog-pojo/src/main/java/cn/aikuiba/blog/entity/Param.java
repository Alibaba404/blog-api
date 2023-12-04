package cn.aikuiba.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 博客参数信息表
 *
 * @TableName tb_param
 */
@Data
public class Param implements Serializable {

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 参数键
     */
    private String paramKey;
    /**
     * 参数值
     */
    private String paramValue;
    /**
     * 参数类型(0:普通表单项,1:文件上传表单项)
     */
    private Integer type;
    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime = new Date();
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date updateTime;
    /**
     * 操作人
     */
    private String operatorName;
    /**
     * 参数描述
     */
    private String paramDesc;

}
