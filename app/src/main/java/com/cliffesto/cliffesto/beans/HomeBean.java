package com.cliffesto.cliffesto.beans;

/**
 * Created by Kapil Gehlot on 1/20/2017.
 */

public class HomeBean {
    public String url;
    public String heading;
    public String status;

    public HomeBean(String url, String heading, String status) {
        this.url = url;
        this.heading = heading;
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String name) {
        this.url = name;
    }

    public String getHeading() {
        return heading;
    }

    public String getStatus() {
        return status;
    }
}
