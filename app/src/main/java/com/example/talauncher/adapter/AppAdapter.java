package com.example.talauncher.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.talauncher.MainActivity;
import com.example.talauncher.R;
import com.example.talauncher.model.AppObject;

import java.util.List;

public class AppAdapter extends BaseAdapter {
    Context context;
    List<AppObject> appList;
    int ceilHeight;

    public AppAdapter(Context context, List<AppObject> appList,int ceilHeight) {
        this.context = context;
        this.appList = appList;
        this.ceilHeight = ceilHeight;
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

        // creating custum height for the home screen
        LinearLayout.LayoutParams lp = new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ceilHeight);
        mlayout.setLayoutParams(lp);

        mlayout.setOnClickListener((view1) -> {
//                Intent launchAppIntent = context.getPackageManager().getLaunchIntentForPackage(appList.get(i).getPackageName());
//
//                if(launchAppIntent != null){
//                    context.startActivity(launchAppIntent);
//                }

            ( (MainActivity) context).itemPress(appList.get(i));


        });

        mlayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ( (MainActivity)context).itemLongPressed(appList.get(i));
                return true;
            }
        });
        return v;
    }
}
