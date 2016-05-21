package com.example.my.baidu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.my.baidu.R;
import com.example.my.baidu.other.BaseViewHolder;

/**
 * @Description:gridview的Adapter
 * @author http://blog.csdn.net/finddreams
 */
public class MyGridAdapter extends BaseAdapter {
	private Context mContext;

	public String[] img_text = { "运球", "投篮", "防守", "篮板球", "上篮", "传球",
			"弹跳", "扣篮" };
	public int[] imgs = { R.drawable.yunqiu, R.drawable.toulan,
			R.drawable.fangshou, R.drawable.lanbanqiu,
			R.drawable.shanglan, R.drawable.chuanqiu,
			R.drawable.tantiao, R.drawable.koulan};

	public MyGridAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return img_text.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.grid, parent, false);
		}
		TextView tv = BaseViewHolder.get(convertView, R.id.tv_name);
		ImageView iv = BaseViewHolder.get(convertView, R.id.iv_touxiang);
		iv.setBackgroundResource(imgs[position]);

		tv.setText(img_text[position]);
		return convertView;
	}

}
