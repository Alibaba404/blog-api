package cn.aikuiba.blog.service;

import cn.aikuiba.blog.entity.Param;
import cn.aikuiba.blog.query.ParamQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by 蛮小满Sama at 2023/11/18 10:33
 *
 * @description
 */
public interface IParamService {

    List<Param> findAll();

    Param findOne(Long id);

    void saveOne(Param param);

    void delete(Long id);

    void update(Param param);

    void delBatch(Long[] ids);

    PageInfo<Param> page(ParamQuery paramQuery);

    Map<String, Object> getParams();
}
