package com.cliffesto.cliffesto.beans;

/**
 * Created by Kapil Gehlot on 1/20/2017.
 */

public class EventBean {
    public String url;
    public String heading;
    //public String description;

    public EventBean(String url, String heading) {
        this.url = url;
        this.heading = heading;
        // this.description = description;
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

//    public String getDescription() {
//        return description;
//    }
}
