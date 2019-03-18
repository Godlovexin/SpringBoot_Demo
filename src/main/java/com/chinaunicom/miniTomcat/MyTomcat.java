package com.chinaunicom.miniTomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/*
    由于我们的Web应用是运行在Tomcat中，那么显然，请求必定是先到达Tomcat的。Tomcat对于请求实际上会进行下面的处理：

    第一：提供Socket服务
    Tomcat的启动，必然是Socket服务，只不过它支持HTTP协议而已！
    这里其实可以扩展思考下，Tomcat既然是基于Socket，那么是基于BIO or NIO or AIO呢？

    第二：进行请求的分发
    要知道一个Tomcat可以为多个Web应用提供服务，那么很显然，Tomcat可以把URL下发到不同的Web应用。

    第三：需要把请求和响应封装成request/response
    我们在Web应用这一层，可从来没有封装过request/response的，我们都是直接使用的，这就是因为Tomcat已经为你做好了！*/


/**
 * Created with IntelliJ IDEA.
 * Author:Sun
 * Description:Tomcat的处理流程：把URL对应处理的Servlet关系形成，解析HTTP协议，封装请求/响应对象，利用反射实例化具体的Servlet进行处理即可。
 * Date: Created in 2019-03-18
 */
public class MyTomcat {

    private int port = 8080;

    private Map<String,String> urlServletMap = new HashMap<String,String>();

    public MyTomcat(int port){
        this.port = port;
    }


    public void start(){

        initServletMapping();//初始化URL与对应处理的servlet的关系

        ServerSocket serverSocket = null;

        try{
            serverSocket = new ServerSocket(port);
            System.out.println("MyTomcat is start...");

            while (true){
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();//创建输入流
                OutputStream outputStream = socket.getOutputStream();//创建输出流

                MyRequest myRequest = new MyRequest(inputStream);//传入输入流，并进行分析，获得url和method
                MyResponse myResponse = new MyResponse(outputStream);//传入输出流

                dispatch(myRequest,myResponse);//去ServletMappingConfig.servletMappingList中查询映射，从而对请求分发

                socket.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(serverSocket != null){
                try {
                    serverSocket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }


    private void initServletMapping(){

        for(ServletMapping servletMapping:ServletMappingConfig.servletMappingList){
            urlServletMap.put(servletMapping.getUrl(),servletMapping.getClazz());//所有映射
        }

    }

    public void dispatch(MyRequest myRequest,MyResponse myResponse){

        String clazz = urlServletMap.get(myRequest.getUrl());
        try{

            Class<MyServlet> myServletClass = (Class<MyServlet>)Class.forName(clazz);//返回与给定的字符串名称相关联类或接口的Class对象
            MyServlet myServlet = myServletClass.newInstance();//为类创建一个实例
            myServlet.service(myRequest,myResponse);//调用方法

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        new MyTomcat(8080).start();
    }

}
