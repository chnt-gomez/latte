package com.go.kchin.presenter;

import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.view.BaseActivity;

import java.lang.ref.WeakReference;

/**
 * Created by MAV1GA on 29/12/2016.
 */

public class MainPresenter implements MainMVP.RequiredPresenterOps, MainMVP.PresenterOps {

    //Layer View reference
    private WeakReference<MainMVP.RequiredViewOps> mView;

    //Layer Model reference
    private MainMVP.ModelOps mModel;

    //Configuration change state
    private boolean mIsChangingConf;

    public MainPresenter(MainMVP.RequiredViewOps mView){
        this.mView = new WeakReference<>(mView);
        this.mModel = new MainModel(this);
    }

    /**
     * Sent from Activity after a confguration changes
     * @param view View reference
     */
    @Override
    public void onConfigurationChanged(MainMVP.RequiredViewOps view) {
        mView = new WeakReference<>(view);
    }

    /**
     * Receives {@link BaseActivity#onDestroy()} event
     * @param isChangingConfig Config change state
     */
    @Override
    public void onDestroy(boolean isChangingConfig) {
        mView = null;
        mIsChangingConf = isChangingConfig;
        if (!isChangingConfig){
            mModel.onDestroy();
        }
    }


}
