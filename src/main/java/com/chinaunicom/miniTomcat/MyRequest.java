package com.chinaunicom.miniTomcat;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * Author:Sun
 * Description:创建请求
 * Date: Created in 2019-03-18
 */
public class MyRequest {

    private String url;
    private String method;

    public MyRequest(InputStream inputStream) throws IOException{

        String httpRequest = "";
        byte[] httpRequestBytes = new byte[1204];//存放请求流
        int length = 0;
        if((length = inputStream.read(httpRequestBytes)) > 0){
            httpRequest = new String(httpRequestBytes,0,length);
        }

        //  HTTP请求协议
        //  GET /favicon.ico HTTP/1.1
        //  Accept:*/*
        //  Accept-Encoding:gzp,deflate
        //  User-Agent:
        //  Host:localhost:8080
        //  Connection:Keep-Alive

        /*对HTTP协议进行解析*/
        String httpHead = httpRequest.split("\n")[0];//GET /favicon.ico HTTP/1.1
        url = httpHead.split("\\s")[1];//split("\\s")已经空格分割
        method = httpHead.split("\\s")[0];//GET
        System.out.println(this);

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
