package com.go.kchin.presenter.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.DatabaseEngine;
import com.go.kchin.view.fragment.BaseFragment;
import java.lang.ref.WeakReference;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class BaseActivity extends AppCompatActivity implements MainMVP.RequiredPresenterOps,
        MainMVP.PresenterOps {

    protected final String TAG = getClass().getSimpleName();

    protected MainMVP.ModelOps mModel;
    protected WeakReference<MainMVP.RequiredViewOps> mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void onOperationSuccess(String message) {
        mView.get().showSnackBar(message);
    }

    @Override
    public void onOperationError(String message) {

    }

    @Override
    public String getStringResource(int stringResource) {
        return getResources().getString(stringResource);
    }

    protected void init() {
        mModel = DatabaseEngine.getInstance(this);
    }

    protected void attachFragment(BaseFragment fragment){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_holder, fragment, null).commit();
        mView = new WeakReference<MainMVP.RequiredViewOps>(fragment);
    }

    protected void lod(String message){
        Log.d(TAG, message);
    }

    protected void low(String message){
        Log.w(TAG, message);
    }

    @Override
    public void onConfigurationChanged(MainMVP.RequiredViewOps view) {

    }

    @Override
    public void onDestroy(boolean isChangingConfig) {

    }

    @Override
    public void moveToActivity(Class type) {
        Intent intent = new Intent(this, type);
        startActivity(intent);
    }
}
