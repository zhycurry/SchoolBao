package com.swufe.schoolbao;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private static final String TAG = "ViewPagerAdapter";
    
    private List<View> mViewList;

    public ViewPagerAdapter(List<View> mViewList) {
        Log.i(TAG, "ViewPagerAdapter: ");
        this.mViewList = mViewList;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.i(TAG, "destroyItem: ");
        container.removeView(mViewList.get(position));
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.i(TAG, "instantiateItem: ");
        container.addView(mViewList.get(position));
        return (mViewList.get(position));
    }

}