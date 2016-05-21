package com.example.my.baidu.Bean;

import java.io.Serializable;

/**
 * Created by 4261305 on 2016/5/13.
 */
public class Index implements Serializable {
    private static final long serialVersionUID = -6919461967497580385L;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    private String num;
}
