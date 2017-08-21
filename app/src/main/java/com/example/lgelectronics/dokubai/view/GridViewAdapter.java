package com.example.lgelectronics.dokubai.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.lgelectronics.dokubai.R;
/**
 * Created by LG Electronics on 2017/08/20.
 */
//Custom Adapter
public class GridViewAdapter extends BaseAdapter {

    Context context;
    Bitmap img[];
    LayoutInflater inf;
    int layout;
    //int img[];

    public GridViewAdapter(Context context, int layout, Bitmap[] img) {
        this.context = context;
        this.layout = layout;
        this.img = img;
        inf = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return img.length;
    }
    @Override
    public Object getItem(int position) {
        return img[position];
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView==null)
            convertView = inf.inflate(layout, null);
        ImageView iv = (ImageView)convertView.findViewById(R.id.image_view_row);
        //iv.setImageResource(img[position]);
        iv.setImageBitmap(img[position]);

        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageActivity.class);
                intent.putExtra("imageBitmap", img[position]);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
