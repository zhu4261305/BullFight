package com.example.my.baidu.Bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 4261305 on 2016/5/11.
 */
public class Quliao extends BmobObject{
    private  String name;
    private  BmobFile picFile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobFile getPicFile() {
        return picFile;
    }

    public void setPicFile(BmobFile picFile) {
        this.picFile = picFile;
    }
}
