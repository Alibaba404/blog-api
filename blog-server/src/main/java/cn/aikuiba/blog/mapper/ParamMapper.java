package cn.aikuiba.blog.mapper;

import cn.aikuiba.blog.entity.Param;
import cn.aikuiba.blog.query.ParamQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/20 11:09
 *
 * @description
 */
@Mapper
public interface ParamMapper {

    List<Param> findAll();

    Param findOne(Long uid);

    void insert(Param param);

    void delete(Long uid);

    void update(Param param);

    void deleteBatch(Long[] ids);

    List<Param> page(ParamQuery paramQuery);

}
