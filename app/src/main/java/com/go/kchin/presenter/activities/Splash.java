package com.go.kchin.presenter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.go.kchin.model.DatabaseEngine;

/**
 * Created by MAV1GA on 06/03/2017.
 */

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){

        //Init Database.
        DatabaseEngine.prepare();

        Intent intent;
        if (getPreferences(MODE_PRIVATE).getBoolean("has_completed_intro", false)) {
            intent = new Intent(this, HomeActivity.class);
        }else{
            intent = new Intent(this, AppIntroActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
