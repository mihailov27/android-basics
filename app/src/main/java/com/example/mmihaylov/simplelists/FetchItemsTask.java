package com.example.mmihaylov.simplelists;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class FetchItemsTask extends AsyncTask<Void, Void, List<String>> {

    private static final Logger LOGGER = Logger.getLogger(FetchItemsTask.class.getName());
    private static final Random random = new Random();

    private int startIndex;
    private ResponseListener<List<String>> responseListener;

    public FetchItemsTask(int startIndex, ResponseListener<List<String>> responseListener) {
        this.startIndex = startIndex;
        this.responseListener = responseListener;
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        try {
            Thread.sleep(getRandomLong());
        } catch (InterruptedException ie) {
            LOGGER.severe("Fail to sleep thread.\n" + ie.getMessage());
        }

        // fetch a maximum of 50 items
        int end = startIndex + 50;

        // get the data
        List<String> dummyItems = new ArrayList<>();
        for(int i=startIndex; i < end; i++) {
            String item = "Item " + i;
            dummyItems.add(item);
        }
        return dummyItems;
    }

    private long getRandomLong() {
        return (random.nextInt(10) * 100) + 1000;
    }

    @Override
    protected void onPostExecute(List<String> items) {
        // give the response items back to the activity
        responseListener.onResponse(items);
    }
}
