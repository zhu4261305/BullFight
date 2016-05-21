package com.example.my.baidu.Utils;

import com.example.my.baidu.Bean.Lost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MyJson {



	public List<Lost> getShopList(String value) {
		List<Lost> list = null;
		try {
			JSONArray jay = new JSONArray(value);
			list = new ArrayList<Lost>();
			for (int i = 0; i < jay.length(); i++) {
				JSONObject job = jay.getJSONObject(i);
				Lost lost = new Lost();
				lost.setDescribe(job.getString("describe"));
				lost.setTitle(job.getString("title"));


				list.add(lost);
			}
		} catch (JSONException e) {
		}
		return list;
	}


}
