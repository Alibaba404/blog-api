package cn.aikuiba.blog.entity;

import cn.aikuiba.system.entity.IPAddress;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 文章信息表
 *
 * @TableName tb_article
 */
@Data
@Document(collection = "t_article")
public class Article implements Serializable {

    /**
     * 主键ID
     *
     * @Id 将实体类ID作为Mongodb的_id, 但是必须在新增xml配置三个属性
     * <insert id="insert" useGeneratedKeys="true" keyColumn="id" keyProperty="id">
     */
    @Id
    private Long id;
    /**
     * 文章封面
     */
    private String articlePic;
    /**
     * 文章名称
     */
    private String articleName;
    /**
     * 文章标签
     */
    private String articleTag;
    /**
     * 文章类型
     */
    private Long articleType;

    /*文章类型名称*/
    private String articleTypeName;
    /**
     * 文章状态(0:草稿;1:发布;2:弃用)
     */
    private Integer articleState;
    /**
     * 是否置顶(0:不置顶;1:置顶)
     */
    private Integer topState;
    /**
     * 作者ID
     */
    private Long publishId;

    /*
     *  作者姓名
     *  */
    private String publishUserName;
    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    /**
     * 文章阅读量
     */
    private Integer articleReadCount = 0;
    /**
     * 文章点赞数
     */
    private Integer articleStarNum = 0;
    /**
     * 文章评论数
     */
    private Integer articleCommentNum = 0;
    /**
     * 文章简介
     */
    private String articleRemark;
    /**
     * 文章内容
     */
    private String articleContent;

    private String year;

    private String month;

    private String day;

    private List<Article> articleList;
    /*方案1:点赞的ip列表*/
    private List<IPAddress> ips;
    /*方案2:动态查询*/
    private Boolean isStarClick = false;
}
