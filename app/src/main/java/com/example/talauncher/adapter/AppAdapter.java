package com.example.talauncher.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.talauncher.R;
import com.example.talauncher.model.AppObject;

import java.util.List;

public class AppAdapter extends BaseAdapter {
    Context context;
    List<AppObject> appList;

    public AppAdapter(Context context, List<AppObject> appList) {
        this.context = context;
        this.appList = appList;
    }

    public int getCount() {
        return appList.size();
    }

    @Override
    public Object getItem(int i) {
        return appList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup parent) {

        View v;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_app,parent, false);

        }else {
            v = view;
        }


        LinearLayout mlayout = v.findViewById(R.id.layout);
        ImageView mimage = v.findViewById(R.id.image);
        TextView mlabel = v.findViewById(R.id.label);

        mimage.setImageDrawable(appList.get(i).getImage());
        mlabel.setText(appList.get(i).getName());

        mlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchAppIntent = context.getPackageManager().getLaunchIntentForPackage(appList.get(i).getPackageName());

                if(launchAppIntent != null){
                    context.startActivity(launchAppIntent);
                }

            }
        });


        return v;
    }
}
