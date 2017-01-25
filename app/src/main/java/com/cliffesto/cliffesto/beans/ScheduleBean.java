package com.cliffesto.cliffesto.beans;

/**
 * Created by Kapil Gehlot on 1/24/2017.
 */

public class ScheduleBean {
    public String url;
    public String heading;
    public String time;

    public ScheduleBean(String url, String heading, String time) {
        this.url = url;
        this.heading = heading;
        this.time = time;
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

    public String getTime() {
        return time;
    }
}
