package com.go.kchin.presenter.activities;


import com.go.kchin.R;
import com.go.kchin.view.fragment.SettingsFragment;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void init() {
        super.init();
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_holder, SettingsFragment.newInstance(), null).
                commit();
    }
}
