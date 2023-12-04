package cn.aikuiba.blog.controller;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/12/4 11:11
 *
 * @description
 */
@Data
public class PageDataBean {
    private List<T> dataList;

    private PageObject pageObj = new PageObject();

    public PageDataBean(List<T> dataList, Long totalRow, Integer pageIndex, Integer pageRowNum) {
        this.dataList = dataList;
        pageObj.setPageIndex(pageIndex);
        pageObj.setPageRowNum(pageRowNum);
        pageObj.setTotalRow(totalRow);
        pageObj.setTotalPage(totalRow / pageRowNum + (totalRow % pageRowNum == 0 ? 0 : 1));
    }
}
