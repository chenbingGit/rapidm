package com.simple.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component//被spring容器管理
@Order(1)//如果多个自定义ApplicationRunner，用来标明执行顺序
public class Executor implements ApplicationRunner {
    private Logger logger = LoggerFactory.getLogger(Executor.class);

    private ArrayList<String[]>  ArrayList1   = new ArrayList<>();
    @Override
    public void run(ApplicationArguments applicationArguments){
        radeopenid();
    }

    //识别新名单
    private void radeopenid(){
//        String filename = "C:\\Users\\chenbing\\Desktop\\openids.txt";
//        File file = new File(filename);
//        String token = httpUtil.getForObject("http://pushserver1.eqianzhuang.com/weixin/token-info-xrgw.do").trim();
//        try {
//            InputStreamReader inputStreamReader = new InputStreamReader( new FileInputStream(file));
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String openid = bufferedReader.readLine();
//            while (!StringUtils.isEmpty(openid)){
//                util.queryUserLocals(openid,token);
//                openid = bufferedReader.readLine();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
