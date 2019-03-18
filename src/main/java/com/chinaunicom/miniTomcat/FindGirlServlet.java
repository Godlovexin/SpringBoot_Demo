package com.chinaunicom.miniTomcat;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Author:Sun
 * Description:
 * Date: Created in 2019-03-18
 */
public class FindGirlServlet extends MyServlet{

    @Override
    public void doGet(MyRequest myRequest, MyResponse myResponse) {
        try{
            myResponse.write("get gril...");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(MyRequest myRequest, MyResponse myResponse) {
        try{
            myResponse.write("post gril...");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
