package com.dudwo.gyrocounter;

/**
 * Created by dudwo on 2017-06-01.
 */

public class Result {

    private String id;
    private String category;
    private String type;
    private int count;
    private int time;

    public Result() {
    }

    public Result(String id, String category, String type, int count, int time) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.count = count;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoty() {return category; }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
