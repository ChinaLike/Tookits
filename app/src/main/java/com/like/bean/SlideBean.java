package com.like.bean;

/**
 * @ describe: 侧滑项
 * @ author: Like on 2017-01-22.
 * @ email: 572919350@qq.com
 */

public class SlideBean {

    private String name;
    private String url;
    private int imageId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", imageId=" + imageId +
                '}';
    }
}
