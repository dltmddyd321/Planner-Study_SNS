package com.example.login_ex.communityadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.login_ex.R;

public class SearchImageAdapter extends BaseAdapter {

    private Context context;

    public int[] allImageArray = {
            R.drawable.certificate, R.drawable.commu, R.drawable.cookieopen, R.drawable.cookiewhat
            ,R.drawable.dojagi, R.drawable.dogwalking
    };

    public SearchImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return allImageArray.length;
    }

    @Override
    public Object getItem(int i) {
        return allImageArray[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView imageView = new ImageView(context);
        imageView.setImageResource(allImageArray[i]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(340,350));

        return imageView;
    }
}

