package com.example.my.baidu.jiekou;

import com.example.my.baidu.Bean.Lost;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 4261305 on 2016/4/5.
 */
public class Serializab implements Serializable {
  private List<Lost> lost;


    public List<Lost> getLosts() {
        return lost;
    }

    public void setLosts(List<Lost> losts) {
        this.lost = losts;
    }


}
