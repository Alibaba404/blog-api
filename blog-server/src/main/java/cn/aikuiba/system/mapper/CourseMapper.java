package cn.aikuiba.system.mapper;

import cn.aikuiba.system.entity.Course;
import cn.aikuiba.system.query.CourseQuery;
import cn.aikuiba.system.query.LogsQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/12/3 10:25
 *
 * @description
 */
@Mapper
public interface CourseMapper {

    List<Course> findAll();

    Course findOne(Long id);

    void insert(Course course);

    void delete(Long id);

    void update(Course course);

    void deleteBatch(Long[] ids);

    List<Course> page(CourseQuery courseQuery);

    void insertBatch(List<Course> courses);

    List<Course> findByIds(Long[] ids);
}
