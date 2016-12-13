package com.go.kchin.activities;

import android.support.v7.app.AppCompatActivity;

import com.go.kchin.interfaces.SearchService;

/**
 * Created by MAV1GA on 13/12/2016.
 */

public class SearchableActivity extends AppCompatActivity {

    SearchService searchCallback;

    public void setSearchService(SearchService arg) {
        this.searchCallback = arg;
    }
}
