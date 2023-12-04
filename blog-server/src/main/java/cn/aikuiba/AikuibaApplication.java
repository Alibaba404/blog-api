package cn.aikuiba;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Created by 蛮小满Sama at 2023/11/17 19:21
 *
 * @description
 */
@EnableCaching   //开启SpringCache
@MapperScan("cn.aikuiba.**.mapper")
@SpringBootApplication
public class AikuibaApplication {
    public static void main(String[] args) {
        SpringApplication.run(AikuibaApplication.class, args);
    }
}
