package com.github.florent37.shapeofview.sample;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ListenableTabLayout extends TabLayout {

    private final List<ScrollListener> scrollListeners = new ArrayList<>();
    private ViewPager viewPager;
    private final List<OnAddedToViewPager> onAddedToViewPagerListeners = new ArrayList<>();

    public ListenableTabLayout(Context context) {
        super(context);
    }

    public ListenableTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListenableTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addScrollListener(ScrollListener scrollListener) {
        scrollListeners.add(scrollListener);
    }

    public void removeScrollListener(ScrollListener scrollListener) {
        scrollListeners.remove(scrollListener);
    }

    public void addOnAddedToViewPagerListener(OnAddedToViewPager listener) {
        onAddedToViewPagerListeners.add(listener);
        if(viewPager != null){
            listener.onAddedToViewPager(this, viewPager);
        }
    }

    public void removeOnAddedToViewPagerListener(OnAddedToViewPager listener) {
        onAddedToViewPagerListeners.remove(listener);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        for (ScrollListener scrollListener : scrollListeners) {
            if (scrollListener != null) {
                scrollListener.onScrollChanged(this, l, t, oldl, oldt);
            }
        }
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        super.setupWithViewPager(viewPager);
        this.viewPager = viewPager;
        for (OnAddedToViewPager onAddedToViewPagerListener : onAddedToViewPagerListeners) {
            onAddedToViewPagerListener.onAddedToViewPager(this, viewPager);
        }
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public interface ScrollListener {
        void onScrollChanged(ListenableTabLayout listenableTabLayout, int l, int t, int oldl, int oldt);
    }

    public interface OnAddedToViewPager {
        void onAddedToViewPager(ListenableTabLayout tabLayout, ViewPager viewPager);
    }
}
