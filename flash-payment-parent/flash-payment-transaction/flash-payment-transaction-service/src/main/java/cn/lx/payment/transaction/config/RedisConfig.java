package cn.lx.payment.transaction.config;

import cn.lx.payment.cache.Cache;
import cn.lx.payment.transaction.util.RedisCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * cn.lx.payment.transaction.config
 *
 * @Author Administrator
 * @date 17:29
 */
@Configuration
public class RedisConfig {

    @Bean
    public Cache cache(StringRedisTemplate stringRedisTemplate){
        return new RedisCache(stringRedisTemplate);
    }

}
