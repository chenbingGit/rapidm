package com.chenbing.rapidm.util;

import com.alibaba.fastjson.JSONObject;
import com.chenbing.rapidm.config.HttpUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class WeChatMigrate {

    private static final String OPENID_MAP_URL = "http://api.weixin.qq.com/cgi-bin/changeopenid?access_token=%s";

    protected Log logger= LogFactory.getLog(this.getClass());

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HttpUtil httpUtil;


    //迁移mysql数据
    @Async("asyncWorker")
    public void mysqlMigrate(int min ,int  max) {
        int row = jdbcTemplate.update("UPDATE credit_cpa.v3_weixin_openid_temporary_xyb2 b  LEFT JOIN credit_agency.v2_chat_log a ON a.openId = b.oldOpenId SET a.openId = b.newOpenId WHERE b.id > ? and b.id < ?;", min,max);
        logger.info("替换" + row +"行,id="+min);
    }

    //迁移mongo数据
    @Async("asyncWorker")
    public void mongoMigrate(String ori_openid,String new_openid) {
        logger.info(ori_openid+","+new_openid);
        try {
            //修改mongoDB数据
            Query query = new Query(Criteria.where("openid").is(ori_openid));
            Update update = new Update().set("openid",new_openid);
            //更新查询返回结果集的第一条
            mongoTemplate.updateFirst(query,update,"chats");
            mongoTemplate.updateFirst(query,update,"locals");
        }catch (Exception e){
            logger.error("修改失败" + ori_openid,e);
        }

    }

    //迁移redis数据
    @Async("asyncWorker")
    public void redisMigrate(String ori_openid,String redisKeyPrefix) {
        try {
            String new_openid = jdbcTemplate.queryForObject("SELECT t.newOpenId FROM credit_cpa.v3_weixin_openid_temporary_xyr2 t WHERE oldOpenId =  ? ;" ,String.class,ori_openid);
            logger.info(ori_openid+","+new_openid);
            redisTemplate.rename(redisKeyPrefix +ori_openid, redisKeyPrefix +new_openid);
        }catch (EmptyResultDataAccessException e){
            logger.error("修改失败没有对应openid,"+ori_openid );
            redisTemplate.delete(redisKeyPrefix +ori_openid);
        }catch (RedisSystemException e){
            redisTemplate.delete(redisKeyPrefix +ori_openid);
        }catch (Exception e){
            logger.error("修改失败" + ori_openid,e);
        }
    }

    @Async("asyncWorker")
    public void subscribeTest(){
        httpUtil.postForObject("http://preweixin.eqianzhuang.com/insurance/xmlMsgReceive","<xml>\n" +
                "\t<ToUserName><![CDATA[gh_e09334361044]]></ToUserName>\n" +
                "\t<FromUserName><![CDATA[ozBTCt_6j-HbM3BzhqbP84-gg0go]]></FromUserName>\n" +
                "\t<CreateTime>1517932879</CreateTime>\n" +
                "\t<MsgType><![CDATA[event]]></MsgType>\n" +
                "\t<Event><![CDATA[subscribe]]></Event>\n" +
                "\t<EventKey><![CDATA[]]></EventKey>\n" +
                "</xml>");
    }


    //查询城市组Id
    private String selectCityIdsByGroupID(String mobileArea){
        Map K = jdbcTemplate.queryForMap(String.format("SELECT a.cityIds FROM credit_cpa.v3_dispatchcityb_group a WHERE id=%s;",mobileArea));
        return (String) K.get("cityIds");
    }

    //查询用户的经纬度
    public void queryUserLocals(String openid){
        Query query = new Query(Criteria.where("openid").is(openid));
        Document documentList = mongoTemplate.findOne(query,Document.class ,"locals");
        ArrayList locals  = (ArrayList)documentList.get("locals");
        long time =0;
        int index=0;
        long time1 ;
        for ( int i=0 ;i < locals.size(); i++){
            Document local = (Document)locals.get(i);
            time1 = local.getLong("t");
            if(time < time1 ){
                index=i;
                time = time1;
            }
        }
        Document local_index = (Document)locals.get(index);
        String x =String.valueOf(local_index.getDouble("x")) ;
        String y =String.valueOf(local_index.getDouble("y")) ;
        String result = httpUtil.getForObject("http://api.map.baidu.com/geocoder/v2/?ak=54HMN2Dp8RGVIw9WK8YzfvpS1RnQTQvr&location="+x+","+y+"&output=json&pois=1");
        JSONObject jsonResult =    JSONObject.parseObject(result).getJSONObject("result");
        if ( jsonResult == null){
            System.out.println(result);
            System.exit(0);
        }
        String  formatted_address = jsonResult.getString("formatted_address");

        if (formatted_address.contains("深圳")) {
            String WEB_PRODLIST_LINK ="【有奖招募】尊敬的用户，小融顾问服务升级啦~现召集首批体验官参与服务体验。<a href=\"http://www.eqianzhuang.com/yj.html?openid=%s\">→详情查看←</a>";
        }
        System.out.println(openid + ":"+ formatted_address);
    }


    //读取文本数据
    private void readText(){
        String filename = "C:\\Users\\chenbing\\Desktop\\openids.txt";
        File file = new File(filename);
        String token = httpUtil.getForObject("http://pushserver1.eqianzhuang.com/weixin/token-info-xrgw.do").trim();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader( new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String openid = bufferedReader.readLine();
            while (!StringUtils.isEmpty(openid)){

                openid = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    @Component
    public class WeChatMigrateRun{

        @Autowired
        private WeChatMigrate weChatMigrate;

        @Autowired
        private RedisTemplate<String,String> redisTemplate;

        @Autowired
        private JdbcTemplate jdbcTemplate;

        protected Log logger= LogFactory.getLog(this.getClass());

        public void redisMigrateRun(){

            String redisKeyPrefix = "UserStatus_wxba7248accdeb1e56";
            Set<String> keySet = redisTemplate.keys(redisKeyPrefix +"o2QdT0*");
            logger.error( "keySet="+keySet.size());
            for (String key : keySet) {
                String openid = key.substring( redisKeyPrefix.length(),key.length());
                weChatMigrate.redisMigrate(openid,redisKeyPrefix);
            }
            logger.info("替换结束");
        }

        public void mongoMigrateRun(){
            for (int i = 0;true; i++) {
                List<Map<String, Object>> openidMapList = jdbcTemplate.queryForList("SELECT t.newOpenId ,t.oldOpenId FROM credit_cpa.v3_weixin_openid_temporary_xyb2 t LIMIT ?,1000;" , i*1000);
                for (Map<String, Object> openidMap:openidMapList) {
                    String ori_openid = (String) openidMap.get("oldOpenId");
                    String new_openid = (String) openidMap.get("newOpenId");
                    weChatMigrate.mongoMigrate(ori_openid,new_openid);
                }
                if (openidMapList.size() < 1000) {
                    logger.info("替换结束");
                    break;
                }
            }

        }

        public void mysqlMigrateRun(){
            int headId = jdbcTemplate.queryForObject("SELECT id FROM credit_cpa.v3_weixin_openid_temporary_xyb2 ORDER BY id ASC LIMIT 1;" , Integer.class);
            int tailId = jdbcTemplate.queryForObject("SELECT id FROM credit_cpa.v3_weixin_openid_temporary_xyb2 ORDER BY id DESC LIMIT 1;" , Integer.class);
            while (true) {
                weChatMigrate.mysqlMigrate( headId , headId + 100);
                headId += 100;
                if ( headId > tailId  ) {
                    logger.info("替换结束");
                    break;
                }
            }
        }
    }
}
