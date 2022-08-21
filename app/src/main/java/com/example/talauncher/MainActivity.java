package com.example.talauncher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.talauncher.adapter.AppAdapter;
import com.example.talauncher.adapter.viewPagerAdapter;
import com.example.talauncher.model.AppObject;
import com.example.talauncher.model.PagerObject;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    boolean isbottom = true;
    ViewPager mViewPager;
    int ceilHeight;
    int NUMBER_OF_ROWS = 5;
    int DRAWER_PEEK_HEIGHT = 100;
    String PREFS_NAME = "NovaPrefs";

    int numRow = 0, numColumn = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getPermission();
        getData();
        

        // hiding action bar
        getSupportActionBar().hide();


        final LinearLayout mTopDrawerLayout = findViewById(R.id.topDrawerLayout);
//        mTopDrawerLayout.post(() -> {
//            DRAWER_PEEK_HEIGHT = mTopDrawerLayout.getHeight();
//            initializeHome();
//            initializeDrawer();
//        });
        mTopDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                DRAWER_PEEK_HEIGHT = mTopDrawerLayout.getHeight();
                initializeHome();
                initializeDrawer();
            }
        });

        ImageView mSettings =findViewById(R.id.settings);
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });






    }

    private void getData(){
        ImageView mHomeScreenImage = findViewById(R.id.homeScreenImage);
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String uri = sharedPreferences.getString("uri",null);
        int numRow = sharedPreferences.getInt("numRow", 7);
        int numColumn = sharedPreferences.getInt("numColumn", 5);

        if(this.numRow != numRow || this.numColumn != numColumn){
            this.numColumn = numColumn;
            this.numRow = numRow;
            initializeHome();
        }

        if(uri != null){
            mHomeScreenImage.setImageURI(Uri.parse(uri));
        }
    }

    private void getPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    viewPagerAdapter mViewPagerAdapter;

    private void initializeHome() {
        ArrayList<PagerObject> pagerAppList = new ArrayList<>();

        ArrayList<AppObject> appList1 =  new ArrayList<>();
        ArrayList<AppObject> appList2 =  new ArrayList<>();
        ArrayList<AppObject> appList3 =  new ArrayList<>();

        for(int i=0;i< numColumn * numRow ;i++){
            appList1.add(new AppObject("","",getResources().getDrawable(R.drawable.ic_launcher_foreground),false));

        }
        for(int i=0;i< numColumn * numRow ;i++){
            appList2.add(new AppObject("","",getResources().getDrawable(R.drawable.ic_launcher_foreground),false));

        }
        for(int i=0;i< numColumn * numRow ;i++){
            appList3.add(new AppObject("","",getResources().getDrawable(R.drawable.ic_launcher_foreground),false));

        }

        pagerAppList.add(new PagerObject(appList1));
        pagerAppList.add(new PagerObject(appList2));
        pagerAppList.add(new PagerObject(appList3));

        ceilHeight = (getDisplayContentHeight() - DRAWER_PEEK_HEIGHT) / numRow;

        mViewPager = findViewById(R.id.viewPager);
        mViewPagerAdapter = new viewPagerAdapter(this, pagerAppList, ceilHeight, numColumn);
        mViewPager.setAdapter(mViewPagerAdapter);
    }



    List<AppObject> installedAppList = new ArrayList<>();

    GridView mDrawerGridView;
    BottomSheetBehavior mBottomSheetBehavior;

    private void initializeDrawer() {
        View mBottomSheet = findViewById(R.id.bottomSheet);
        mDrawerGridView = findViewById(R.id.drawerGrid);

        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);

        mBottomSheetBehavior.setHideable(false);
        mBottomSheetBehavior.setPeekHeight(DRAWER_PEEK_HEIGHT);
//
        // adding manually
//        for(int i=0 ;i<20; i++){
//            appList.add(new AppObject(String.valueOf(i),"",getDrawable(R.drawable.ic_launcher_foreground)));
//        }

        installedAppList = getInstalledAppList();

        mDrawerGridView.setAdapter(new AppAdapter(this, installedAppList, ceilHeight));

//        mBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//
//                if(mAppDrag != null){
//                    return;
//                }
//
//                if(newState == BottomSheetBehavior.STATE_COLLAPSED && mDrawerGridView.getChildAt(0).getY() != 0){
//                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                }
//                if(newState == BottomSheetBehavior.STATE_DRAGGING && mDrawerGridView.getChildAt(0).getY() != 0){
//                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//            }
//        });


        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if(mAppDrag != null){
                    return;
                }

                if(newState == BottomSheetBehavior.STATE_COLLAPSED && mDrawerGridView.getChildAt(0).getY() != 0){
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                if(newState == BottomSheetBehavior.STATE_DRAGGING && mDrawerGridView.getChildAt(0).getY() != 0){
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }

 // for adding app on the homescreen

    public AppObject mAppDrag = null;
    // when icon is pressed
    public void itemPress(AppObject app){

        if(mAppDrag != null && !app.getName().equals("")){
            Toast.makeText(this,"Cell already occupied",Toast.LENGTH_SHORT).show();
            return;
        }

        if(mAppDrag != null && !app.getAppInDrawer()){
                app.setPackageName(mAppDrag.getPackageName());
                app.setName(mAppDrag.getName());
                app.setImage(mAppDrag.getImage());
                app.setAppInDrawer(false);

                if(!mAppDrag.getAppInDrawer()){
                    mAppDrag.setPackageName("");
                    mAppDrag.setName("");
                    mAppDrag.setImage(getResources().getDrawable(R.drawable.ic_launcher_foreground));
                    mAppDrag.setAppInDrawer(false);
                }
                mAppDrag = null;
                mViewPagerAdapter.notifiGridChanged();
                return;
        }else {
            Intent launchAppIntent = getApplicationContext().getPackageManager().getLaunchIntentForPackage(app.getPackageName());
            if(launchAppIntent != null){
                getApplicationContext().startActivity(launchAppIntent);
            }
        }
    }

    // when item is long pressed
    public void itemLongPressed(AppObject app){
       collapseDrawer();
       mAppDrag = app;
    }

    private void collapseDrawer() {
        mDrawerGridView.setY(DRAWER_PEEK_HEIGHT);
        // we are collapseing the drawer view
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
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

            AppObject app = new AppObject(appName,appPackageName,appImage,true);
            if(!list.contains(app)){
                list.add(app);
            }

        }

        return list;
    }

    private int getDisplayContentHeight() {
        final WindowManager windowManager = getWindowManager();
        final Point size = new Point();
        int screenHeight;
        int actionBarHeight = 0;
        int statusBarHeight = 0;

        if(getActionBar() != null){
            actionBarHeight = getActionBar().getHeight();
        }
        int resourceId = getResources().getIdentifier("status_bar_height","dimen","android");
        if(resourceId > 0){
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        int contentTop = (findViewById(android.R.id.content)).getTop();
        windowManager.getDefaultDisplay().getSize(size);
        screenHeight = size.y;
        return screenHeight - contentTop - actionBarHeight - statusBarHeight;
    }
}