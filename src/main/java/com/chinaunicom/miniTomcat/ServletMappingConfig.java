package com.chinaunicom.miniTomcat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author:Sun
 * Description:我们在servlet开发中，会在web.xml中通过和来进行指定哪个URL交给哪个servlet进行处理。
 * Date: Created in 2019-03-18
 */
public class ServletMappingConfig {

    public static List<ServletMapping> servletMappingList = new ArrayList<>();

    static {
        servletMappingList.add(new ServletMapping("findGirl","/girl","com.chinaunicom.miniTomcat.FindGirlServlet"));
        servletMappingList.add(new ServletMapping("helloWorld","/world","com.chinaunicom.miniTomcat.HelloWorldServlet"));
    }

}
