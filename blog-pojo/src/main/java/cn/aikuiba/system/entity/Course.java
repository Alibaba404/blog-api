package cn.aikuiba.system.entity;

import cn.hutool.core.annotation.Alias;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 蛮小满Sama at 2023/12/3 11:08
 *
 * @description 课程实体类
 */
@Data
public class Course implements Serializable {
    private String id;
    @Alias("课程日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lectureTime;
    @Alias("星期")
    private String week;
    @Alias("课程内容")
    private String content;
    @Alias("授课讲师")
    private String teacher;
    @Alias("备注")
    private String remark;
}
