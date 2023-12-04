package cn.aikuiba.blog.controller;

import lombok.Data;

/**
 * Created by 蛮小满Sama at 2023/12/4 11:12
 *
 * @description
 */
@Data
public class PageObject {
    // 当前页
    private long pageIndex;
    // 当前页数
    private long pageRowNum;
    // 总页数
    private long totalPage;
    // 总数量
    private long totalRow;
}
