package cn.aikuiba.system.controller;

import cn.aikuiba.resp.R;
import cn.aikuiba.system.aop.LogsAnno;
import cn.aikuiba.system.auth.PermissionAnno;
import cn.aikuiba.system.entity.Logs;
import cn.aikuiba.system.query.LogsQuery;
import cn.aikuiba.system.service.ILogsService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 日志接口
 * Created by 蛮小满Sama at 2023/11/18 10:35
 *
 * @description
 */
@RestController
@RequestMapping("/logs")
public class LogsController {

    private final ILogsService logsService;

    @Autowired
    public LogsController(ILogsService logsService) {
        this.logsService = logsService;
    }

    /**
     * 查询全部日志信息
     *
     * @return
     */
    @PermissionAnno(name = "获取全部日志", sn = "logs:findAll", descs = "获取全部日志")
    @GetMapping
    public R<List<Logs>> findAll() {
        return R.success(logsService.findAll());
    }

    /**
     * 根据ID查询日志
     *
     * @param id 日志ID
     * @return
     */
    @PermissionAnno(name = "获取单个日志", sn = "logs:findOne", descs = "根据日志ID获取日志信息")
    @GetMapping("/{id}")
    public R<Logs> findOne(@PathVariable("id") Long id) {
        return R.success(logsService.findOne(id));
    }

    /**
     * 新增/修改日志信息
     *
     * @param logs 日志信息
     */
    @PermissionAnno(name = "保存日志", sn = "logs:save", descs = "保存或修改日志信息")
    @PutMapping
    public R<String> saveOrUpdate(@RequestBody Logs logs) {
        try {
            String message = "添加成功!";
            if (null == logs.getId()) {
                logsService.saveOne(logs);
            } else {
                logsService.update(logs);
                message = "修改成功!";
            }
            return R.success(200, message);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(1002, "服务器异常", e.getMessage());
        }
    }

    /**
     * 根据ID删除一个日志信息
     *
     * @param id 日志ID
     */
    @PermissionAnno(name = "删除单个日志", sn = "logs:delOne", descs = "根据日志ID删除日志信息")
    @DeleteMapping("/{id}")
    public R<Void> delOne(@PathVariable("id") Long id) {
        logsService.delete(id);
        return R.success();
    }

    /**
     * 批量删除日志信息
     *
     * @param ids 日志ID数组
     * @return
     */
    @PermissionAnno(name = "删除多个日志", sn = "logs:delBatch", descs = "批量删除")
    @PatchMapping
    public R<Void> delBatch(@RequestBody Long[] ids) {
        logsService.delBatch(ids);
        return R.success();
    }

    /**
     * 分页查询
     *
     * @param logsQuery
     * @return
     */
    @PermissionAnno(name = "分页查询", sn = "logs:page", descs = "分页查询")
    @PostMapping
    public R<PageInfo<Logs>> page(@RequestBody LogsQuery logsQuery) {
        logsQuery.setOrderBy("tl.create_time DESC");
        return R.success(logsService.page(logsQuery));
    }

}
