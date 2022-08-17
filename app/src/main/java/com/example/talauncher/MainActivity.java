package com.example.talauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.example.talauncher.adapter.AppAdapter;
import com.example.talauncher.model.AppObject;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeDrawer();
    }

    List<AppObject> installedAppList = new ArrayList<>();

    private void initializeDrawer() {
        View mBottomSheet = findViewById(R.id.bottomSheet);
        final GridView mDrawerGridView = findViewById(R.id.drawerGrid);

        BottomSheetBehavior mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);

//        mBottomSheetBehavior.setHideable(false);
//        mBottomSheetBehavior.setPeekHeight(300);
//
        // adding manually
//        for(int i=0 ;i<20; i++){
//            appList.add(new AppObject(String.valueOf(i),"",getDrawable(R.drawable.ic_launcher_foreground)));
//        }

        installedAppList = getInstalledAppList();

        mDrawerGridView.setAdapter(new AppAdapter(getApplicationContext(), installedAppList));
    }

    private List<AppObject> getInstalledAppList() {
        List<AppObject> list = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> untreatedAppList = getApplicationContext().getPackageManager().queryIntentActivities(intent,0);

        for(ResolveInfo untreatedApp : untreatedAppList){
            String appName = untreatedApp.activityInfo.loadLabel(getPackageManager()).toString();
            String appPackageName = untreatedApp.activityInfo.packageName;
            Drawable appImage = untreatedApp.activityInfo.loadIcon(getPackageManager());

            AppObject app = new AppObject(appName,appPackageName,appImage);
            if(!list.contains(app)){
                list.add(app);
            }

        }

        return list;
    }
}