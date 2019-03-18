package com.chinaunicom.miniTomcat;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * Author:Sun
 * Description:基于HTTP协议的格式进行输出写入
 * Date: Created in 2019-03-18
 */
public class MyResponse {

    private OutputStream outputStream;

    public MyResponse(OutputStream outputStream){
        this.outputStream = outputStream;
    }

    public void write(String content) throws IOException{

        //  HTTP响应协议
        //  HTTP/1.1 200 OK
        //  Content-Type:text/html
        //
        //  <html><body></body></html>

        StringBuffer httpResponse = new StringBuffer();
        httpResponse.append("HTTP/1.1 200 OK\n")
                .append("Content-Type:text/html\n")
                .append("\r\n")
                .append("<html><body>")
                .append(content)
                .append("</body></html>");

        outputStream.write(httpResponse.toString().getBytes());//放入输出流
        outputStream.close();


    }

}
