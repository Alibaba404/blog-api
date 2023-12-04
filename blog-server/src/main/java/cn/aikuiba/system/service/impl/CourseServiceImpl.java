package cn.aikuiba.system.service.impl;

import cn.aikuiba.system.entity.Course;
import cn.aikuiba.system.mapper.CourseMapper;
import cn.aikuiba.system.query.CourseQuery;
import cn.aikuiba.system.service.ICourseService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 蛮小满Sama at 2023/11/22 14:13
 *
 * @description
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> findAll() {
        return courseMapper.findAll();
    }

    @Override
    public Course findOne(Long id) {
        return courseMapper.findOne(id);
    }

    @Override
    public void saveOne(Course course) {
        courseMapper.insert(course);
    }

    @Override
    public void delete(Long id) {
        courseMapper.delete(id);
    }

    @Override
    public void update(Course course) {
        courseMapper.update(course);
    }

    @Override
    public void delBatch(Long[] ids) {
        courseMapper.deleteBatch(ids);
    }

    @Override
    public PageInfo<Course> page(CourseQuery courseQuery) {
        PageHelper.startPage(courseQuery.getCurrentPage(), courseQuery.getPageSize());
        PageHelper.orderBy(courseQuery.getOrderBy());
        List<Course> courses = courseMapper.page(courseQuery);
        return new PageInfo<>(courses);
    }

    private String nullToString(Object obj, String format) {
        if (null == obj) return "";
        if ("null".equals(obj)) return "";
        if (null != format && !"".equals(format.trim())) {
            return (String.valueOf(obj)).split(" ")[0];
        }
        return obj.toString();
    }

    private String nullToString(Object obj) {
        return this.nullToString(obj, null);
    }

    @Transactional
    @Override
    public void importCourse(MultipartFile file) {
        try {
            List<Map<String, Object>> maps = ExcelUtil.getReader(file.getInputStream()).readAll();
            List<Course> courses = new ArrayList<>(maps.size());
            for (Map<String, Object> map : maps) {
                String lectureTime = nullToString(map.get("日期"), "yyyy-MM-dd");
                String week = nullToString(map.get("星期"));
                String content = nullToString(map.get("课程内容"));
                String teacher = nullToString(map.get("讲师"));
                String remark = nullToString(map.get("备注"));
                Course course = new Course();
                course.setLectureTime(DateUtil.parse(lectureTime));
                course.setWeek(week);
                course.setContent(content);
                course.setTeacher(teacher);
                course.setRemark(remark);
                courses.add(course);
            }
            courseMapper.insertBatch(courses);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void exportCourse(Long[] ids, HttpServletResponse response) {
        try {
            // 导出表格的文件名
            String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String fileName = URLEncoder.encode("Course-" + today + ".xlsx", StandardCharsets.UTF_8);
            List<Course> courses = courseMapper.findByIds(ids);
            // 将文件流写入到输出流
            ExcelWriter writer = ExcelUtil.getWriter(true);
            writer.write(courses, true);
            //设置响应的mime类型
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
//            response.setContentType("application/octet-stream");
            //设置文件名 attachment  不要直接下载,要弹框选择位置
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            //获取输出流
            ServletOutputStream out = response.getOutputStream();
            writer.autoSizeColumnAll();
            writer.flush(out, true);
            // 关闭writer，释放内存
            writer.close();
            //此处记得关闭输出Servlet流
            IoUtil.close(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
