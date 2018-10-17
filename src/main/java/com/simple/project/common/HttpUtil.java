package com.simple.project.common;

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

    public HttpUtil(RestTemplate restTemplate, HttpHeaders headersForm, HttpHeaders headersJson, HttpHeaders headersXml) {
        this.restTemplate = restTemplate;
        this.headersForm = headersForm;
        this.headersJson = headersJson;
        this.headersXml = headersXml;

    }

    public String postForObject(String Url, String parameter){

        HttpEntity<String> requestEntity = new HttpEntity<>(parameter, headersXml);
        String result = restTemplate.postForObject(Url,requestEntity,String.class);
        logger.info("url <"+ Url +"> 参数<" + parameter + "> 结果<"+ result +">");
        return restTemplate.postForObject(Url,requestEntity,String.class);
    }

    public String postForObject(String Url, JSONObject parameter){

        HttpEntity<String> requestEntity = new HttpEntity<>(parameter.toJSONString(), headersJson);
        String result = restTemplate.postForObject(Url,requestEntity,String.class);
        logger.info("url <"+ Url +"> 参数<" + parameter + "> 结果<"+ result +">");
        return result;
    }

    public String postForObject(String Url, LinkedMultiValueMap<String, String> parameter){
        HttpEntity<LinkedMultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameter, headersForm);
        String result = restTemplate.postForObject(Url,requestEntity,String.class);
        logger.info("url <"+ Url +"> 参数<" + parameter + "> 结果<"+ result +">");
        return result;
    }

    public String getForObject(String Url){
        return restTemplate.getForObject(Url,String.class).trim();
    }
}
