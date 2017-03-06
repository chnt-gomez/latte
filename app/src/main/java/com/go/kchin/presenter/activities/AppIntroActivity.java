package com.go.kchin.presenter.activities;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.go.kchin.R;

/**
 * Created by MAV1GA on 06/03/2017.
 */

public class AppIntroActivity extends AppIntro{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        final int bgColor = Color.parseColor("#1c2430");
        super.onCreate(savedInstanceState);
        setFadeAnimation();
        addSlide(AppIntroFragment.newInstance(getString(R.string.welcome_to_go_sales),
                getString(R.string.welcome_summary), R.drawable.ic_launcher, bgColor));
        addSlide(AppIntroFragment.newInstance(getString(R.string.intro_products),
                getString(R.string.intro_products_summary), R.drawable.ic_launcher, bgColor));
        addSlide(AppIntroFragment.newInstance(getString(R.string.intro_materials),
                getString(R.string.intro_materials_summary), R.drawable.ic_launcher, bgColor));
        addSlide(AppIntroFragment.newInstance(getString(R.string.intro_sales),
                getString(R.string.intro_sales_summary), R.drawable.ic_launcher, bgColor));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        onContinue();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        onContinue();
    }

    private void onContinue(){
        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putBoolean("has_completed_intro", true).apply();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        this.finish();
    }
}
