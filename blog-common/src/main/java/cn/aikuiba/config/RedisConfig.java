package cn.aikuiba.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * Created by 蛮小满Sama at 2023/11/20 19:27
 *
 * @description
 */
@Configuration
public class RedisConfig {

    // 注入Redis连接工厂
    @Resource
    private RedisConnectionFactory factory;

    /**
     * @Description: 自定义RedisTemplate对象注入Bean容器中
     * @Author: Neuronet
     * @Date: 2023/10/22 18:49
     **/
    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        // 1.创建一个RedisTemplate对象
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        // 2.指定Redis连接工厂
        redisTemplate.setConnectionFactory(factory);
        // 3.创建一个JSON格式序列化对象，此处使用的是Redis自己的的序列化器
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        // 4.指定除开hash数据类型之外的数据key和value使用什么序列化器
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        // 5.指定hash数据类型的key和value序列化器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);
        return redisTemplate;
    }
}
