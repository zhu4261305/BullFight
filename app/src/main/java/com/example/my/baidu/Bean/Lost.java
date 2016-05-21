package com.example.my.baidu.Bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/** ����ʧ�����
  * @ClassName: Lost
  * @Description: TODO
  * @author smile
  * @date 2014-5-21 ����11:27:03
  */
public class Lost extends BmobObject implements Serializable {




    private  String num;

   private String time;


   private  String weizhi;



	private String title;
	private String describe;
	private String photo;
	private String place;
	public String getWeizhi() {
		return weizhi;
	}

	public void setWeizhi(String weizhi) {
		this.weizhi = weizhi;
	}
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	private BmobFile picFile;




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
