package com.chinaunicom.sun.springboot.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created with IntelliJ IDEA.
 * Author:Sun
 * Description:
 * Date: Created in 2018-04-03
 */
@ConfigurationProperties(prefix = "com.dudu")
public class ConfigBean {

    private String name;
    private String want;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWant() {
        return want;
    }

    public void setWant(String want) {
        this.want = want;
    }
}
