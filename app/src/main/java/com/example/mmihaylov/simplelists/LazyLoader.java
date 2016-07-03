package com.example.mmihaylov.simplelists;

import android.widget.AbsListView;

public abstract class LazyLoader implements AbsListView.OnScrollListener {

    private static final int DEFAULT_THRESHOLD = 10;

    private boolean loading = true;
    private int previousTotal = 0;
    private int threshold = DEFAULT_THRESHOLD;

    public LazyLoader() {}

    public LazyLoader(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(loading) {
            if(totalItemCount > previousTotal) {
                // loading has finished
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        // check if the list needs more data
        if(!loading) {
            if(firstVisibleItem + visibleItemCount >= totalItemCount - threshold) {
                loading = true;
                // list needs more data - fetch data
                loadMore(absListView, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        }
    }

    public abstract void loadMore(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount);
}
