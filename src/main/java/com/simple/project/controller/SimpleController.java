package com.simple.project.controller;

import com.alibaba.fastjson.JSONObject;
import com.simple.project.common.HttpUtil;
import com.simple.project.constant.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


/**
 * 微信公众号平台功能相关控制器
 * @author chenbing
 *
 */
@RequestMapping(value = "/simple/project")
@RestController
public class SimpleController {

    private static Log logger = LogFactory.getLog(SimpleController.class);

    @Autowired
    HttpUtil httpUtil;


    @RequestMapping(value = "/message",method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public ResponseEntity<Map<String,Object>> offlineProdApply(Map userInfo)
    {
        HashMap<String,Object> map = new HashMap<>();
        map.put("data", Constant.SIMPLE_LIFE);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }


    @RequestMapping(value = "/sendMsg",method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public ResponseEntity<Map<String,Object>> sendMsg()
    {   
        
        String s = "您再多申请两个产品就有可能下款啦\n\n" +
                "👉<a href=\"http://xyr.591jq.cn/efinancial/ai/to-online?openid=%s&channel=AI_AUTO_XYR_0&lenderId=115101&groupId=16001&urlId=890\">菠萝贷-现金贷</a>👈\n\n" +
                "👉<a href=\"http://xyr.591jq.cn/efinancial/ai/to-online?openid=%s&channel=AI_AUTO_XYR_0&lenderId=125527&groupId=25027&urlId=1007\">借钱花-现金贷</a>👈\n\n" +
                "👉<a href=\"http://xyr.591jq.cn/efinancial/ai/to-online?openid=%s&channel=AI_AUTO_XYR_0&lenderId=118901&groupId=16001&urlId=891\">优借-现金贷</a>👈\n\n" +
                "👉<a href=\"http://xyr.591jq.cn/efinancial/ai/to-online?openid=%s&channel=AI_AUTO_XYR_0&lenderId=123801&groupId=16001&urlId=928\">信用白条-现金贷</a>👈\n\n" +
                "👉<a href=\"http://xyr.591jq.cn/efinancial/ai/to-online?openid=%s&channel=AI_AUTO_XYR_0&lenderId=106001&groupId=16001&urlId=862\">花无尽-现金贷</a>👈\n\n" +
                "👉<a href=\"http://xyr.591jq.cn/efinancial/ai/to-online?openid=%s&channel=AI_AUTO_XYR_0&lenderId=113601&groupId=16001&urlId=874\">借花花钱包-现金贷</a>👈\n\n" +
                "👉<a href=\"http://xyr.591jq.cn/efinancial/ai/to-online?openid=%s&channel=AI_AUTO_XYR_4&lenderId=107101&groupId=13030&urlId=876\">51人品贷-极速版</a>👈\n\n" +
                "👉<a href=\"http://xyr.591jq.cn/efinancial/ai/to-online?openid=%s&channel=AI_AUTO_XYR_0&lenderId=102901&groupId=25027&urlId=667\">小赢卡贷</a>\uD83D\uDC48\n";
        String SEND_MESSAGE_BY_CUSTOM = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";
        String accessToken="14_J71toRTNcDYAWpiqeDppSi1VE1qYJk0FJxgZBitlQtvc1RuC7IrH6Uw5pI92W0VJY_4KCMPfs46ZGkvwf4j-6gUWoBBPR5M6s0vtyEdRZ7DHiW-xBCdX8SySDkVOCMtMkD0n-aMki1d0Fff1VQLbAEAGBW";


        String filename = "C:\\Users\\chenbing\\Desktop\\openids";
        File file = new File(filename);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader( new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String openid = bufferedReader.readLine();
            JSONObject msgJson = new JSONObject();
            msgJson.put("msgtype","text");
            JSONObject textJson = new JSONObject();
            int i=0;
            while (!StringUtils.isEmpty(openid)){
                i++;
                if (i>2000){
                    textJson.put("content",String.format(s,openid,openid,openid,openid,openid,openid,openid,openid));
                    msgJson.put("text",textJson);
                    msgJson.put("touser",openid);
                    //发送客服消息
                    httpUtil.postForObject(String.format(SEND_MESSAGE_BY_CUSTOM, accessToken),msgJson);
                }
                openid = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap<String,Object> map = new HashMap<>();
        map.put("data", Constant.SIMPLE_LIFE);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

}
