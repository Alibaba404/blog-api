package cn.aikuiba.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/20 11:06
 *
 * @description 文章类型
 */
@Data
public class ArticleType {
    private Long id;
    private String typeName;
    /*可用状态:0:不可用;1:可用*/
    private Integer status;
    /*创建时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime = new Date();
    /*更新时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    /*父类型ID*/
    private Long parentId;
    /*父类型名称*/
    private String parentName;
    /*子类型*/
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ArticleType> children = new ArrayList<>();
}
