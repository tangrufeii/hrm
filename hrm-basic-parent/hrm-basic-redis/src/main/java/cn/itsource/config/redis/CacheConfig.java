package cn.itsource.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * @description: Redis缓存配置
 */
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisConnectionFactory factory;

    /**
     * 向Spring环境中声明一个 RedisTemplate 对象
     *
     * Redis默认使用 JdkSerializationRedisSerializer 对象进行序列化，可能会产生16进制的数据（看起来像乱码），被序列化的对象必须实现Serializable接口
     *
     * 为了方便我们查看，我们可以使用 JacksonJsonRedisSerializer 或 GenericJackson2JsonRedisSerializer
     * 上面两者都能序列化成JSON，但是后者会在JSON中加入@class属性，类的全路径包名，方便反序列化。
     * 前者如果存放了List，则在反序列化的时候如果没指定TypeReference，会报错java.util.LinkedHashMap cannot be cast to xxxxx(某dto)
     * 原因：序列化带泛型的数据时，会以map的结构进行存储，反序列化是不能将map解析成对象。
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        //序列化器
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        //String数据key的序列化
        redisTemplate.setKeySerializer(genericJackson2JsonRedisSerializer);
        //String数据value的序列化
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);

        //hash结构key的序列化
        redisTemplate.setHashKeySerializer(genericJackson2JsonRedisSerializer);
        //hash结构value的序列化
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        return redisTemplate;
    }

    //缓存解析器
    @Bean
    @Override
    public CacheResolver cacheResolver() {
        return new SimpleCacheResolver(cacheManager());
    }

    //缓存管理器
    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues() //不缓存null
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));//存到Redis的数据使用JSON进行序列化，方便我们查看
        return RedisCacheManager.builder(factory).cacheDefaults(cacheConfiguration).build();
    }
}
