package cn.aikuiba;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * Created by 蛮小满Sama at 2023/11/17 19:21
 *
 * @description
 */
@Slf4j
@Component
public class AikuibaApplicationRunner implements ApplicationRunner {
    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Iterator<String> iterator = args.getOptionNames().iterator();
        while (iterator.hasNext()) {
            log.info("项目启动完成!==>{}", iterator.next());
        }
        log.info("=====================项目启动完成=====================");
    }
}
