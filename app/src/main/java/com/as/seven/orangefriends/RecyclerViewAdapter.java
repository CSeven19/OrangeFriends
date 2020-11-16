package com.as.seven.orangefriends;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

/**
 * RecyclerViewAdapter
 *
 * @author linzhiyong
 * @time 2017年2月8日15:17:41
 * @email wflinzhiyong@163.com
 * @desc
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;

    private List<Integer> data;

    private List<String> data_tv;

    public RecyclerViewAdapter(Context context, List<Integer> data, List<String> data_tv) {
        this.context = context;
        this.data = data;
        this.data_tv = data_tv;
    }

    // recycleview 布局
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview, parent, false));
        return holder;
    }

    //  recycleview数据绑定
    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        holder.imageView.setImageResource(this.data.get(position));
        holder.textView.setText(this.data_tv.get(position));
//        if (position % 2 == 0) {
        holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
        holder.textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
//        } else {
//            holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
//            holder.textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120));
//        }
        //设置点击和长按事件
//        if (mItemClickListener != null){
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mItemClickListener.onItemClick(position);
//                }
//            });
//        }
        if (mLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return mLongClickListener.onLongClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView1);
            this.textView = itemView.findViewById(R.id.textView1);
        }
    }
//    private AdapterView.OnItemClickListener mItemClickListener;
    private OnLongClickListener mLongClickListener;


//    public void setItemClickListener(OnItemClickListener itemClickListener) {
//        mItemClickListener = (AdapterView.OnItemClickListener) itemClickListener;
//    }

    public void setLongClickListener(OnLongClickListener longClickListener) {
        mLongClickListener = longClickListener;
    }
//    public interface OnItemClickListener{
//        void onItemClick(int position);
//    }
    public interface OnLongClickListener {
        boolean onLongClick(int position);
    }
}