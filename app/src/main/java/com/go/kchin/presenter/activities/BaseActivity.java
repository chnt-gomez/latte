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

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class BaseActivity extends AppCompatActivity implements MainMVP.RequiredPresenterOps,
        MainMVP.PresenterOps{

    protected MainMVP.ModelOps mModel;
    protected MainMVP.RequiredViewOps mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mModel != null){
           mModel.onConfigurationChanged(this);
        }

    }

    @Override
    public void onOperationSuccess(String message, long rowId) {
        mView.showOperationResult(message, rowId);
    }

    @Override
    public void onOperationSuccess(String message) {
        mView.showSnackBar(message);
    }

    @Override
    public void onOperationError(String message) {

    }

    @Override
    public String getStringResource(int stringResource) {
        return getResources().getString(stringResource);
    }

    protected void init() {
        if (mModel == null) {
            mModel = DatabaseEngine.getInstance(this);
        }
    }


    protected void attachFragment(BaseFragment fragment){
        Log.d(getClass().getSimpleName(), "Fragment attached");
        mView = fragment;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_holder, fragment, null).commit();
    }


    @Override
    public void moveToActivity(Class type, Bundle args) {
        Intent intent = new Intent(this, type);
        startActivity(intent);
    }

}
