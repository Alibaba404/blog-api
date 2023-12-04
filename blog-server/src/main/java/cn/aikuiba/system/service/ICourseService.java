package cn.aikuiba.system.service;


import cn.aikuiba.system.entity.Course;
import cn.aikuiba.system.query.CourseQuery;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/22 11:07
 *
 * @description
 */
public interface ICourseService {

    List<Course> findAll();

    Course findOne(Long id);

    void saveOne(Course course);

    void delete(Long id);

    void update(Course course);

    void delBatch(Long[] ids);

    PageInfo<Course> page(CourseQuery courseQuery);

    void importCourse(MultipartFile file);

    void exportCourse(Long[] ids,HttpServletResponse response);
}
