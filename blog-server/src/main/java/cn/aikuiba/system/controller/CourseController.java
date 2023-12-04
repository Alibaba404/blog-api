package cn.aikuiba.system.controller;

import cn.aikuiba.resp.R;
import cn.aikuiba.system.auth.PermissionAnno;
import cn.aikuiba.system.entity.Course;
import cn.aikuiba.system.query.CourseQuery;
import cn.aikuiba.system.service.ICourseService;
import cn.hutool.poi.excel.ExcelUtil;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 课程接口
 * Created by 蛮小满Sama at 2023/11/18 10:35
 *
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {

    private final ICourseService courseService;

    @Autowired
    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * 查询全部课程信息
     *
     * @return
     */
    @PermissionAnno(name = "获取全部课程", sn = "course:findAll", descs = "获取全部课程")
    @GetMapping
    public R<List<Course>> findAll() {
        return R.success(courseService.findAll());
    }

    /**
     * 根据ID查询课程
     *
     * @param id 课程ID
     * @return
     */
    @PermissionAnno(name = "获取单个课程", sn = "course:findOne", descs = "根据课程ID获取课程信息")
    @GetMapping("/{id}")
    public R<Course> findOne(@PathVariable("id") Long id) {
        return R.success(courseService.findOne(id));
    }

    /**
     * 新增/修改课程信息
     *
     * @param course 课程信息
     */
    @PermissionAnno(name = "保存课程", sn = "course:save", descs = "保存或修改课程信息")
    @PutMapping
    public R<String> saveOrUpdate(@RequestBody Course course) {
        try {
            String message = "添加成功!";
            if (null == course.getId()) {
                courseService.saveOne(course);
            } else {
                courseService.update(course);
                message = "修改成功!";
            }
            return R.success(200, message);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(1002, "服务器异常", e.getMessage());
        }
    }

    /**
     * 根据ID删除一个课程信息
     *
     * @param id 课程ID
     */
    @PermissionAnno(name = "删除单个课程", sn = "course:delOne", descs = "根据课程ID删除课程信息")
    @DeleteMapping("/{id}")
    public R<Void> delOne(@PathVariable("id") Long id) {
        courseService.delete(id);
        return R.success();
    }

    /**
     * 批量删除课程信息
     *
     * @param ids 课程ID数组
     * @return
     */
    @PermissionAnno(name = "删除多个课程", sn = "course:delBatch", descs = "批量删除")
    @PatchMapping
    public R<Void> delBatch(@RequestBody Long[] ids) {
        courseService.delBatch(ids);
        return R.success();
    }

    /**
     * 分页查询
     *
     * @param courseQuery
     * @return
     */
    @PermissionAnno(name = "分页查询", sn = "course:page", descs = "分页查询")
    @PostMapping
    public R<PageInfo<Course>> page(@RequestBody CourseQuery courseQuery) {
        return R.success(courseService.page(courseQuery));
    }

    @PermissionAnno(name = "课程导入", sn = "course:importCourse", descs = "上传课程表格导入课程")
    @PostMapping("/importCourse")
    public R<Void> importCourse(@RequestPart("file") MultipartFile file) {
        courseService.importCourse(file);
        return R.success();
    }

    @RequestMapping("/exportCourse")
    public void exportCourse(Long[] ids, HttpServletResponse response) {
        courseService.exportCourse(ids, response);
    }

}
