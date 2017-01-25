package com.cliffesto.cliffesto.beans;

/**
 * Created by Kapil Gehlot on 1/20/2017.
 */

public class TeamBean {
    public String url;
    public String heading;
    public String mobile;
    public String description;

    public TeamBean(String url, String heading, String mobile, String description) {
        this.url = url;
        this.heading = heading;
        this.description = description;
        this.mobile = mobile;
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

    public String getDescription() {
        return description;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile() {
        this.mobile = mobile;
    }
}
