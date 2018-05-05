package com.chinaunicom.sun.springboot.service;

import com.chinaunicom.sun.springboot.controller.UserController;
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

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author:Sun
 * Description:
 * Date: Created in 2018-05-05
 */
public class HttpClientService {
    private Logger logger = Logger.getLogger(HttpClientService.class);

    public HttpGetInfo httpGetByType(String url, String param, Map<String, String> header) {
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
