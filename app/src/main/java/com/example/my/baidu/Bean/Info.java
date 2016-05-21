package com.example.my.baidu.Bean;

import com.example.my.baidu.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Info implements Serializable
{
	private static final long serialVersionUID = -1010711775392052966L;
	private double latitude;
	private double longitude;
	private int imgId;
	private String name;
	private String distance;
	private int zan;

	public static List<Info> infos = new ArrayList<Info>();

	static
	{
		infos.add(new Info(34.612614,119.226559, R.drawable.img1, "江苏省连云港市新浦区苍梧路59号",
				"����209��", 1456));
		infos.add(new Info(34.608525,119.199692, R.drawable.im1, "新浦区龙河南路24号附近(近海连东路)",
				"����897��", 456));
		infos.add(new Info(34.579428,119.143276, R.drawable.im2, "江苏省连云港市海州区新建西路88号",
				"����249��", 1456));
		infos.add(new Info(34.592882,119.1967, R.drawable.im3, "江苏连云港淮海工学院",
				"����679��", 1456));
		infos.add(new Info(34.601556,119.176944, R.drawable.im3, "中国江苏省连云港市海州区市场路32号",
				"����679��", 1456));
		infos.add(new Info(34.578921,119.148999, R.drawable.im3, "新建中路38号附近",
				"����679��", 1456));
		infos.add(new Info(34.60795,119.224388, R.drawable.im3, "苍梧路58号",
				"����679��", 1456));
		infos.add(new Info(34.59455,119.22946, R.drawable.im3, "建设东路",
				"����679��", 1456));
		infos.add(new Info(34.617271,119.185016, R.drawable.im3, "连云港市新浦区)",
				"����679��", 1456));
	}

	public Info(double latitude, double longitude, int imgId, String name,
				String distance, int zan)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		this.imgId = imgId;
		this.name = name;
		this.distance = distance;
		this.zan = zan;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public int getImgId()
	{
		return imgId;
	}

	public void setImgId(int imgId)
	{
		this.imgId = imgId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDistance()
	{
		return distance;
	}

	public void setDistance(String distance)
	{
		this.distance = distance;
	}

	public int getZan()
	{
		return zan;
	}

	public void setZan(int zan)
	{
		this.zan = zan;
	}

}
