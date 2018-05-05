package com.chinaunicom.sun.springboot.controller;

import com.chinaunicom.sun.springboot.model.ConfigBean;
import com.chinaunicom.sun.springboot.model.HttpGetInfo;
import net.sf.json.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author:Sun
 * Description:
 * Date: Created in 2018-04-03
 */
@RestController
public class UserController {
    private Logger logger = Logger.getLogger(UserController.class);
/*
    @Value("${com.dudu.name}")
    private  String name;
    @Value("${com.dudu.want}")
    private  String want;
*/
@Autowired
ConfigBean configBean;

    @RequestMapping("/")
    public String hexo(){



        Map header = new HashMap();
        header.put("Content-Type", "application/json");
        header.put("Accept", "application/json");
        //header.put("WWW-authenticate", "admin:Harbor12345");
        logger.info("haha");



        HttpGetInfo httpGetInfo =httpGetByType("http://10.124.142.43:18080/acs/api/v1/users", "", header);

        String param = "{ \"project_name\": \"string\", \"public\": 0, \"enable_content_trust\": true, \"prevent_vulnerable_images_from_running\": true, \"prevent_vulnerable_images_from_running_severity\": \"string\", \"automatically_scan_images_on_push\": true}";
      //  HttpGetInfo httpPostInfo = httpPostByType("http://10.124.142.131/api/projects",param,header);

        return configBean.getName()+","+configBean.getWant();
    }

    public  HttpGetInfo httpGetByType(String url, String param, Map<String, String> header) {
        HttpGetInfo httpGetInfo = new HttpGetInfo();
        // 初始化
        String result = null;
        try {
            HttpClient httpclient = HttpClients.createDefault();


/*            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("encoding", "UTF-8"));
            nvps.add(new BasicNameValuePair("name", "admin"));
            nvps.add(new BasicNameValuePair("password", "Harbor12345"));*/

            //HttpPost post = new HttpPost(url);
            HttpGet get = new HttpGet(url);
            String encoding="sunqx17:123456";


            for (Map.Entry<String, String> entry : header.entrySet()) {
                get.setHeader(entry.getKey(), entry.getValue());
            }
            logger.info("url=" + url + ";param=" + param);

            StringEntity se = new StringEntity(param, "UTF-8");
            /*get.setEntity(se);
            get.setEntity(new UrlEncodedFormEntity(nvps));*/
            get.setHeader("Authorization", "Basic " + DatatypeConverter.printBase64Binary((encoding).getBytes("UTF-8")));
            HttpResponse response = httpclient.execute(get);
            int httpCode = response.getStatusLine().getStatusCode();
            if (httpCode == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instreams = entity.getContent();
                    result = new String(convertStreamToString(instreams).getBytes(), "UTF-8");
                    JSONArray json = JSONArray.fromObject(result);
                    System.out.println(json);
                    get.abort();
                }
            } else {
               logger.error("httpCode：" + httpCode + ", reason：" + response.getStatusLine().getReasonPhrase());

                httpGetInfo.setStatus(httpCode);
                httpGetInfo.setMessage(response.getStatusLine().getReasonPhrase());
                return httpGetInfo;
            }

        } catch (Exception e) {
            //logger.error("url：" + url + ", reason：" + e.getMessage());
            httpGetInfo.setStatus(500);
            httpGetInfo.setMessage("Internal Server Error");
            return httpGetInfo;
        }
        //logger.info("post request success");
        httpGetInfo.setStatus(200);
        httpGetInfo.setData(result);
        return httpGetInfo;
    }

    public  HttpGetInfo httpPostByType(String url, String param, Map<String, String> header) {
        HttpGetInfo httpGetInfo = new HttpGetInfo();
        // 初始化
        String result = null;
        try {
            HttpClient httpclient = HttpClients.createDefault();
            String encoding = "admin:Harbor12345";
            HttpPost post = new HttpPost(url);
            for (Map.Entry<String, String> entry : header.entrySet()) {
                post.setHeader(entry.getKey(), entry.getValue());
            }
            logger.info("url=" + url + ";param=" + param);

            StringEntity se = new StringEntity(param, "UTF-8");
            post.setEntity(se);
            post.setHeader("Authorization", "Basic " + DatatypeConverter.printBase64Binary((encoding).getBytes("UTF-8")));
            HttpResponse response = httpclient.execute(post);
            int httpCode = response.getStatusLine().getStatusCode();
            if (httpCode == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instreams = entity.getContent();
                    result = new String(convertStreamToString(instreams).getBytes(), "UTF-8");
                    post.abort();
                }
            } else {
                logger.error("httpCode：" + httpCode + ", reason：" + response.getStatusLine().getReasonPhrase());

                httpGetInfo.setStatus(httpCode);
                httpGetInfo.setMessage(response.getStatusLine().getReasonPhrase());
                return httpGetInfo;
            }

        } catch (Exception e) {
            logger.error("url：" + url + ", reason：" + e.getMessage());
            httpGetInfo.setStatus(500);
            httpGetInfo.setMessage("Internal Server Error");
            return httpGetInfo;
        }
        logger.info("post request success");
        httpGetInfo.setStatus(200);
        httpGetInfo.setData(result);
        return httpGetInfo;
    }
    // 将流转化为字符串
    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
