package com.chenbing.rapidm.executor;

import com.alibaba.fastjson.JSONObject;
import com.chenbing.rapidm.config.HttpUtil;
import com.chenbing.rapidm.dao.UserStatusDao;
import com.chenbing.rapidm.util.WeChatMigrate;
import com.chenbing.rapidm.util.menu.XYJFGWMenuManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component//被spring容器管理
@Order(1)//如果多个自定义ApplicationRunner，用来标明执行顺序
public class Executor implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(Executor.class);

    @Autowired
    private HttpUtil httpUtil;

    @Autowired
    private WeChatMigrate.WeChatMigrateRun weChatMigrateRun;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;


    @Autowired
    private UserStatusDao userStatusDao;

    @Autowired
    private XYJFGWMenuManager XYJFGWMenuManager;


    @Override
    public void run(ApplicationArguments applicationArguments){

        logger.error(JSONObject.toJSONString(userStatusDao.get("ozBTCt_6j-HbM3BzhqbP84-gg0go")));

//        XYJFGWMenuManager.createMenu("16_xosM1K97yJFlckrvxDQ68fYfb5GQpEsQMRZtriBVxHODhiG_BD2N9PHYIeZ4caFIMCBIwmOg5ELKMV5ssjZXadiBdDDOLCg5fMssb_79iS2lRjVyyCLyHXRFukVc-L_SBemuvDnTqZwYNx5gZDOaAGADHS","prexyr.591jq.cn","wxd41629b093ac774a");
    }

}
