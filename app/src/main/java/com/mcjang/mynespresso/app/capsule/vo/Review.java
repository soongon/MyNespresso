package com.mcjang.mynespresso.app.capsule.vo;

/**
 * Created by mcjan on 2016-12-07.
 */

public class Review {

    private String name;
    private float point;
    private String comment;
    private String date;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
