package com.example.talauncher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.talauncher.R;
import com.example.talauncher.model.PagerObject;

import java.util.ArrayList;

public class viewPagerAdapter extends PagerAdapter {

    Context context;
    ArrayList<PagerObject> pagerAppList;
    int ceilHeight, numColumn;
    ArrayList<AppAdapter> appAdapterList = new ArrayList<>();

    public viewPagerAdapter(Context context, ArrayList<PagerObject> pagerAppList, int ceilHeight, int numColumn) {
        this.context = context;
        this.pagerAppList = pagerAppList;
        this.ceilHeight = ceilHeight;
        this.numColumn = numColumn;
    }


    // this is responsible for to show the item in the app
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.pager_layout, container, false);

        final GridView mGridView = layout.findViewById(R.id.grid);
        mGridView.setNumColumns(numColumn);

        AppAdapter mGridAdapter = new AppAdapter(context,pagerAppList.get(position).getAppList(), ceilHeight);
        mGridView.setAdapter(mGridAdapter);

        appAdapterList.add(mGridAdapter);

        container.addView(layout);

        return layout;
    }

    // due to this only after getting all the home screen page also our app will not crash
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return pagerAppList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public void notifiGridChanged(){
        for(int i=0; i<appAdapterList.size(); i++){
            appAdapterList.get(i).notifyDataSetChanged();
        }
    }
}
