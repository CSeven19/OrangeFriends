package com.as.seven.orangefriends;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sf Zhang on 2016/12/20.
 */
public class RecordAdapter extends ArrayAdapter<OneRecord> {

    private int resourceId;
    int[] color = {Color.parseColor("#F5EFA0"), Color.parseColor("#8296D5"), Color.parseColor("#95C77E"), Color.parseColor("#F49393"), Color.parseColor("#FFFFFF")};

    public RecordAdapter(Context context, int resource, List<OneRecord> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OneRecord memo = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        ImageView tag = view.findViewById(R.id.tag);
        TextView textDate = view.findViewById(R.id.textDate);
        TextView mainText = view.findViewById(R.id.mainText);
        tag.setBackgroundColor(color[0]);
        textDate.setText(memo.getCreatetime()!=null?memo.getCreatetime():"");
        mainText.setText(memo.getContent()!=null?memo.getContent():"");

        return view;
    }
}
