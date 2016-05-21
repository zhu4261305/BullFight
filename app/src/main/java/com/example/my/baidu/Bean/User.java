package com.example.my.baidu.Bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 4261305 on 2016/3/24.
 */
public class User extends BmobUser {
    private String weizhi;



    private String use;
    private String name;
    private String something;

    private BmobFile picFile;
    public String getWeizhi() {
        return weizhi;
    }

    public void setWeizhi(String weizhi) {
        this.weizhi = weizhi;
    }

    public BmobFile getPicFile() {
        return picFile;
    }






    public void setPicFile(BmobFile picFile) {
        this.picFile = picFile;
    }

    public String getSomething() {
        return something;
    }

    public void setSomething(String something) {
        this.something = something;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }





}