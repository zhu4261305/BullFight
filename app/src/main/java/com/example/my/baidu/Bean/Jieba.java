package com.example.my.baidu.Bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 4261305 on 2016/5/10.
 */
public class Jieba extends BmobObject implements Serializable {
    private String title;
    private String describe;
    private String photo;
    private String place;




    private BmobFile picFile;
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }




    public BmobFile getPicFile() {
        return picFile;
    }
    public void setPicFile(BmobFile picFile) {
        this.picFile = picFile;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescribe() {
        return describe;
    }
    public void setDescribe(String describe) {
        this.describe = describe;
    }

}
