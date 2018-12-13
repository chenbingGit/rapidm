package com.chenbing.rapidm.config;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

public class HttpUtil {

    private Log logger = LogFactory.getLog(this.getClass());

    private RestTemplate restTemplate;

    private HttpHeaders headersForm;

    private HttpHeaders headersJson;

    private HttpHeaders headersXml;

    public HttpUtil(RestTemplate restTemplate, HttpHeaders headersForm, HttpHeaders headersJson,HttpHeaders headersXml) {
        this.restTemplate = restTemplate;
        this.headersForm = headersForm;
        this.headersJson = headersJson;
        this.headersXml = headersXml;

    }

    public String postForObject(String url, String xmlString){

        HttpEntity<String> requestEntity = new HttpEntity<>(xmlString, headersXml);
        String result = restTemplate.postForObject(url,requestEntity,String.class);
        logger.info("url <"+ url +"> 参数<" + xmlString + "> 结果<"+ result +">");
        return restTemplate.postForObject(url,requestEntity,String.class);
    }

    public String postForObject(String url, JSONObject parameter){

        HttpEntity<String> requestEntity = new HttpEntity<>(parameter.toJSONString(), headersJson);
        String result = restTemplate.postForObject(url,requestEntity,String.class);
        logger.info("url <"+ url +"> 参数<" + parameter + "> 结果<"+ result +">");
        return restTemplate.postForObject(url,requestEntity,String.class);
    }

    public String postForObject(String url, LinkedMultiValueMap<String, String> parameter){
        HttpEntity<LinkedMultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameter, headersForm);
        String result = restTemplate.postForObject(url,requestEntity,String.class);
        logger.info("url <"+ url +"> 参数<" + parameter + "> 结果<"+ result +">");
        return restTemplate.postForObject(url,requestEntity,String.class);
    }

    public String getForObject(String url){
        String result = restTemplate.getForObject(url,String.class).trim();
        logger.info("url <"+ url + "> 结果<"+ result +">");
        return result ;
    }
}
