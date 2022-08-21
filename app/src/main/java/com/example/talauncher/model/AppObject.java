package com.example.talauncher.model;

import android.graphics.drawable.Drawable;
import android.service.controls.actions.BooleanAction;

public class AppObject {

    private String name,
            packageName;
    private Drawable image;
    private Boolean isAppInDrawer;

    public AppObject(String name, String packageName, Drawable image, Boolean isAppInDrawer) {
        this.name = name;
        this.packageName = packageName;
        this.image = image;
        this.isAppInDrawer = isAppInDrawer;
    }

    public Boolean getAppInDrawer() {
        return isAppInDrawer;
    }

    public void setAppInDrawer(Boolean appInDrawer) {
        isAppInDrawer = appInDrawer;
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public Drawable getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
