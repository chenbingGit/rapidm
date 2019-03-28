package com.chenbing.rapidm.config;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableCaching
@EnableAsync
public class ConfigBean {

    @Bean
    public Executor asyncWorker() {
            /** Set the ThreadPoolExecutor's core pool size. */
            int corePoolSize = 10;
            /** Set the ThreadPoolExecutor's maximum pool size. */
            int maxPoolSize = 20;
            /** Set the capacity for the ThreadPoolExecutor's BlockingQueue. */
            int queueCapacity = 5;
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(corePoolSize);
            executor.setMaxPoolSize(maxPoolSize);
            executor.setQueueCapacity(queueCapacity);
            executor.setThreadNamePrefix("Async-Worker-");
            // rejection-policy：当pool已经达到max size的时候，如何处理新任务
            // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            executor.initialize();
            return executor;
    }

    @Bean
    public HttpUtil httpUtil(ClientHttpRequestFactory factory){
        HttpHeaders headersForm = new HttpHeaders();
        headersForm.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headersForm.add("Accept", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        HttpHeaders headersJson = new HttpHeaders();
        headersJson.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headersJson.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        HttpHeaders headersXml = new HttpHeaders();
        headersJson.setContentType(MediaType.TEXT_XML);
        headersJson.add("Accept", MediaType.APPLICATION_XML_VALUE);
        RestTemplate  restTemplate =new RestTemplate(factory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return new HttpUtil(restTemplate ,headersForm,headersJson,headersXml);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(30000);//ms
        factory.setConnectTimeout(30000);//ms
        return factory;
    }


    @Bean
    public RedisTemplate<String, String> redisTemplate(LettuceConnectionFactory factory){

        StringRedisTemplate template = new StringRedisTemplate(factory);
//        template.setValueSerializer();
//        setSerializer(template);//设置序列化工具
        template.afterPropertiesSet();
        return template;
    }

    //缓存管理器
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        return RedisCacheManager
                .builder(factory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)))
                .transactionAware()
                .build();
    }
}

