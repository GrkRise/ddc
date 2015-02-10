package com.example.grk_rise.ddc.model;

/**
 * Created by GrK_Rise on 03.02.2015.
 */
public class NavDrawerItem {
    private String title;
    private int icon;
    private String count = "0";
    private boolean isCounterVisible = false;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setCounterVisible(boolean isCounterVisible) {
        this.isCounterVisible = isCounterVisible;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public String getCount() {
        return count;
    }

    public boolean isCounterVisible() {
        return isCounterVisible;
    }

    public NavDrawerItem(){}

    public NavDrawerItem(String title, int icon)
    {
        this.title = title;
        this.icon = icon;
    }

    public NavDrawerItem(String title, int icon, boolean isCounterVisible, String count)
    {
        this.icon = icon;
        this.title = title;
        this.isCounterVisible = isCounterVisible;
        this.count = count;
    }


}
