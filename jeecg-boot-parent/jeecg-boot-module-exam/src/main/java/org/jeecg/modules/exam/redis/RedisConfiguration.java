package org.jeecg.modules.exam.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author zbm
 * @date 2019-05-30
 */
@Configuration
public class RedisConfiguration {

    @Autowired
    private StringRedisSerializer stringRedisSerializer;

    @Bean(name = "defaultRedisConnectionFactory")
    public JedisConnectionFactory connectionFactory(@Value("${redis.host.default:r-bp1e7d805e493074pd.redis.rds.aliyuncs.com}") String host,
                                                    @Value("${redis.port.default:6379}") int port,
                                                    @Value("${redis.password.default:Weixinhelper2019}") String password,
                                                    @Value("${redis.pool.default.maxTotal:1500}") Integer maxTotal,
                                                    @Value("${redis.pool.default.maxIdle:200}") Integer maxIdle,
                                                    @Value("${redis.pool.default.minIdle:100}") Integer minIdle,
                                                    @Value("${redis.pool.default.maxWaitMillis:10}") Integer maxWaitMillis,
                                                    @Value("${redis.pool.default.testOnBorrow:false}") Boolean testOnBorrow,
                                                    @Value("${redis.pool.default.testOnReturn:false}") Boolean testOnReturn,
                                                    @Value("${redis.pool.default.timeBetweenEvictionRunsMillis:30000}") Integer timeBetweenEvictionRunsMillis,
                                                    @Value("${redis.pool.default.testWhileIdle:true}") Boolean testWhileIdle,
                                                    @Value("${redis.pool.default.numTestsPerEvictionRun:50}") Integer numTestsPerEvictionRun) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        poolConfig.setTestOnBorrow(testOnBorrow);
        poolConfig.setTestOnReturn(testOnReturn);
        poolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        poolConfig.setTestWhileIdle(testWhileIdle);
        poolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setDatabase(0);
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        if (StringUtils.isNotBlank(password)) {
            jedisConnectionFactory.setPassword(password);
        }
        return jedisConnectionFactory;
    }

    @Bean(name = "defaultStringRedisTemplate")
    public RedisTemplate<String, String> stringRedisTemplate(@Qualifier("defaultRedisConnectionFactory") JedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public ValueOperations<String, String> stringOperations(@Qualifier("defaultStringRedisTemplate") RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(@SuppressWarnings("SpringJavaAutowiringInspection") ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "Null objectMapper");
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
                Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }

    @Bean(name = "defaultObjectRedisTemplate")
    public RedisTemplate<String, Object> objectRedisTemplate(@Qualifier("defaultRedisConnectionFactory") JedisConnectionFactory connectionFactory,
                                                             Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public ValueOperations<String, Object> objectOperations(@Qualifier("defaultObjectRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

}
