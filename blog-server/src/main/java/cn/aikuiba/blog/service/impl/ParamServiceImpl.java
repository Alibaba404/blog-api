package cn.aikuiba.blog.service.impl;

import cn.aikuiba.blog.entity.Param;
import cn.aikuiba.blog.mapper.ParamMapper;
import cn.aikuiba.blog.query.ParamQuery;
import cn.aikuiba.blog.service.IParamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by 蛮小满Sama at 2023/11/18 10:34
 *
 * @description
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
@Service
public class ParamServiceImpl implements IParamService {

    @Autowired
    private ParamMapper paramMapper;


    @Override
    public List<Param> findAll() {
        return paramMapper.findAll();
    }

    @Override
    public Param findOne(Long uid) {
        return paramMapper.findOne(uid);
    }

    @Transactional
    @Override
    public void saveOne(Param param) {
        param.setOperatorName("蛮小满Sama");
        paramMapper.insert(param);
    }

    @Transactional
    @Override
    public void delete(Long uid) {
        paramMapper.delete(uid);
    }

    @Transactional
    @Override
    public void update(Param param) {
        param.setUpdateTime(new Date());
        paramMapper.update(param);
    }

    @Transactional
    @Override
    public void delBatch(Long[] ids) {
        paramMapper.deleteBatch(ids);
    }

    @Override
    public PageInfo<Param> page(ParamQuery paramQuery) {
        // 当前页码
        Integer currentPage = paramQuery.getCurrentPage();
        // 分页条数
        Integer pageSize = paramQuery.getPageSize();
        // 开启分页,必须在业务分页查询方法的前面,Mybatis才会拦截
        PageHelper.startPage(currentPage, pageSize);
        return new PageInfo<>(paramMapper.page(paramQuery));
    }

    @Override
    public Map<String, Object> getParams() {
        return this.findAll().stream().collect(Collectors.toMap(Param::getParamKey, Param::getParamValue));
    }

}
