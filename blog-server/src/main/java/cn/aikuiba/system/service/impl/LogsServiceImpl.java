package cn.aikuiba.system.service.impl;

import cn.aikuiba.system.entity.Logs;
import cn.aikuiba.system.mapper.LogsMapper;
import cn.aikuiba.system.query.LogsQuery;
import cn.aikuiba.system.service.ILogsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/22 14:13
 *
 * @description
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
@Service
public class LogsServiceImpl implements ILogsService {

    @Autowired
    private LogsMapper logsMapper;

    @Override
    public List<Logs> findAll() {
        return logsMapper.findAll();
    }

    @Override
    public Logs findOne(Long id) {
        return logsMapper.findOne(id);
    }

    @Override
    public void saveOne(Logs logs) {
        logsMapper.insert(logs);
    }

    @Override
    public void delete(Long id) {
        logsMapper.delete(id);
    }

    @Override
    public void update(Logs logs) {
        logsMapper.update(logs);
    }

    @Override
    public void delBatch(Long[] ids) {
        logsMapper.deleteBatch(ids);
    }

    @Override
    public PageInfo<Logs> page(LogsQuery logsQuery) {
        PageHelper.startPage(logsQuery.getCurrentPage(), logsQuery.getPageSize());
        PageHelper.orderBy(logsQuery.getOrderBy());
        List<Logs> logss = logsMapper.page(logsQuery);
        return new PageInfo<>(logss);
    }

}
