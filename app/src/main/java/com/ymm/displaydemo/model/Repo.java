package com.ymm.displaydemo.model;

/**
 * Created by Administrator on 2016/5/8.
 */
public class Repo {

    public long id;
    public String name;
    public String full_name;
    public String description;
    public Owner owner;

    @Override
    public String toString() {
        return "Repo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
