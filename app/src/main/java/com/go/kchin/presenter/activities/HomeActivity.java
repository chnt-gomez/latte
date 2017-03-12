package com.go.kchin.presenter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.go.kchin.R;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.util.utilities.Dialogs;
import com.go.kchin.view.fragment.HomeFragment;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;


public class HomeActivity extends BaseActivity implements RequiredDialogOps.RequiredPasswordOps{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.start);
    }



    @Override
    protected void init() {
        super.init();
        attachFragment(HomeFragment.newInstance());
    }

    @Override
    protected void onNeedTutorial() {
        // Get the screen with the id

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings){
            if (ifPasswordProtected()) {
                Dialogs.newPasswordDialog(this, getStringResource(R.string.content_protected),
                        getStringResource(R.string.content_protected_summary), this)
                        .show();
            }else{
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void isAuthorized(boolean isAuthorized) {
        if (isAuthorized){
            startActivity(new Intent(this, SettingsActivity.class));
        }else{
            mView.showError(getStringResource(R.string.unauthorized));
        }
    }

    @Override
    public void recoverPassword() {
        mView.showMessage(R.string.recover_password);
    }
}
