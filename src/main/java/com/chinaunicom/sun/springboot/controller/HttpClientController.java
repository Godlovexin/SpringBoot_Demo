package com.chinaunicom.sun.springboot.controller;

import com.chinaunicom.sun.springboot.model.HttpGetInfo;
import com.chinaunicom.sun.springboot.service.HttpClientService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author:Sun
 * Description:HTTPClient测试
 * Date: Created in 2018-05-05
 */
public class HttpClientController {

    private Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private HttpClientService httpClientService;

    @RequestMapping("/httpGet")
    public String httpGet(){



        Map header = new HashMap();
        header.put("Content-Type", "application/json");
        header.put("Accept", "application/json");
        //header.put("WWW-authenticate", "admin:Harbor12345");
        logger.info("haha");



        HttpGetInfo httpGetInfo =httpClientService.httpGetByType("http://10.124.142.43:18080/acs/api/v1/users", "", header);

        String param = "{ \"project_name\": \"string\", \"public\": 0, \"enable_content_trust\": true, \"prevent_vulnerable_images_from_running\": true, \"prevent_vulnerable_images_from_running_severity\": \"string\", \"automatically_scan_images_on_push\": true}";
        //  HttpGetInfo httpPostInfo = httpPostByType("http://10.124.142.131/api/projects",param,header);

        return "";
    }
    @RequestMapping("/httpPost")
    public String httpPost(){

        Map header = new HashMap();
        header.put("Content-Type", "application/json");
        header.put("Accept", "application/json");
        //header.put("WWW-authenticate", "admin:Harbor12345");
        logger.info("haha");



        HttpGetInfo httpGetInfo =httpClientService.httpPostByType("http://10.124.142.43:18080/acs/api/v1/users", "", header);

        String param = "{ \"project_name\": \"string\", \"public\": 0, \"enable_content_trust\": true, \"prevent_vulnerable_images_from_running\": true, \"prevent_vulnerable_images_from_running_severity\": \"string\", \"automatically_scan_images_on_push\": true}";
        //  HttpGetInfo httpPostInfo = httpPostByType("http://10.124.142.131/api/projects",param,header);

        return "";
    }

}
