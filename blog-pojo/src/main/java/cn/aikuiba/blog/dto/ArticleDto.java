package cn.aikuiba.blog.dto;

import lombok.Data;

/**
 * Created by 蛮小满Sama at 2023/11/23 10:57
 *
 * @description
 */
@Data
public class ArticleDto {

    private Long articleTypeId;

    private String articleTypeName;

    private Integer articleTypeCount;

    private String time;

    private Integer articleCount;

}
