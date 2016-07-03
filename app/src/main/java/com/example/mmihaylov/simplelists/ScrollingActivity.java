package com.example.mmihaylov.simplelists;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

public class ScrollingActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;

    private ResponseListener<List<String>> responseListener = new ResponseListener<List<String>>() {
        @Override
        public void onResponse(List<String> responseItems) {
            // append the data to the list view.
            adapter.addAll(responseItems);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        ListView listView = (ListView) findViewById(R.id.list_view);
        // add a progress bar to indicate progress
        listView.addFooterView(new ProgressBar(this));
        // connect adapter
        adapter = new ArrayAdapter<String>(this, R.layout.list_text_item, R.id.text_item);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new LazyLoader() {
            @Override
            public void loadMore(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                loadItems();
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.frame), "List is loaded", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }, 9000);
    }

    private void loadItems() {
        int startIndex = adapter.getCount();
        new FetchItemsTask(startIndex, responseListener).execute();
    }

    public void onNotifyClick(View view) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.frame), "Notify button was clicked.", Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
