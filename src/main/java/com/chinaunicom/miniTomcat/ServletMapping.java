package com.chinaunicom.miniTomcat;

/**
 * Created with IntelliJ IDEA.
 * Author:Sun
 * Description:访问连接映射实体
 * Date: Created in 2019-03-18
 */
public class ServletMapping {

    private String servletName;
    private String url;
    private String clazz;

    public ServletMapping(String servletName,String url,String clazz){

        this.servletName = servletName;
        this.url = url;
        this.clazz = clazz;

    }

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
}
