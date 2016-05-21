package com.example.my.baidu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.my.baidu.R;
import com.example.my.baidu.Utils.SortModel;

import java.util.List;

/**
 * Created by 4261305 on 2016/3/10.
 */
public class SortAdapter extends BaseAdapter implements SectionIndexer {
    private List<SortModel> list = null;
    private Context mContext;


    public SortAdapter(Context mContext, List<SortModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     * @param list
     */
    public void updateListView(List<SortModel> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {


        ViewHolder viewHolder = null;
        final SortModel mContent = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item, null);

            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.tvImage = (ImageView) view.findViewById(R.id.image);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if(position == getPositionForSection(section)){
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getSortLetters());
        }else{
            viewHolder.tvLetter.setVisibility(View.GONE);
        }
        int images[] = new int[]{R.drawable.buxingzhe1,R.drawable.guowang,R.drawable.gongniu,R.drawable.huojian
                ,R.drawable.huren,R.drawable.huosai,R.drawable.nba1,R.drawable.juejin,R.drawable.jueshi,
                R.drawable.kuaichuan,R.drawable.kaituozhe,R.drawable.lanwang,R.drawable.laoying,R.drawable.nba1,R.drawable.moshu
                ,R.drawable.maci,R.drawable.menglong,R.drawable.nikesi,R.drawable.qishi,R.drawable.nba1,
                R.drawable.rehuo,R.drawable.shanmao,R.drawable.senlinlang,R.drawable.taiyang,R.drawable.xionglu,R.drawable.xiaoniu,R.drawable.yongshi,R.drawable.qiliuren};
        for (int i = 0 ; i<images.length ; i++){
            viewHolder.tvImage.setImageResource(images[position]);
        }





        viewHolder.tvTitle.setText(this.list.get(position).getName());

        return view;

    }



    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
        ImageView tvImage;
    }



    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }


    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }


    private String getAlpha(String str) {
        String  sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}