package cn.aikuiba.system.mapper;

import cn.aikuiba.system.entity.Logs;
import cn.aikuiba.system.query.LogsQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by 蛮小满Sama at 2023/12/3 10:25
 *
 * @description
 */
@Mapper
public interface LogsMapper {

    List<Logs> findAll();

    Logs findOne(Long id);

    void insert(Logs logs);

    void delete(Long id);

    void update(Logs logs);

    void deleteBatch(Long[] ids);

    List<Logs> page(LogsQuery logsQuery);
}
