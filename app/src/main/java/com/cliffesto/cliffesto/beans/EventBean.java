package com.cliffesto.cliffesto.beans;

/**
 * Created by Kapil Gehlot on 1/20/2017.
 */

public class EventBean {
    public String url;
    public String heading;
    public EventBean(String url, String heading) {
        this.url = url;
        this.heading = heading;
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
}
