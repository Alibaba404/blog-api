package cn.aikuiba.system.service;


import cn.aikuiba.system.entity.Logs;
import cn.aikuiba.system.query.LogsQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/11/22 11:07
 *
 * @description
 */
public interface ILogsService {

    List<Logs> findAll();

    Logs findOne(Long id);

    void saveOne(Logs logs);

    void delete(Long id);

    void update(Logs logs);

    void delBatch(Long[] ids);

    PageInfo<Logs> page(LogsQuery logsQuery);

}
