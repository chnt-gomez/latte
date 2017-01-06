package com.go.kchin.view.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.go.kchin.interfaces.MainMVP;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class BaseFragment extends Fragment implements MainMVP.RequiredViewOps {

    protected View view;

    @Override
    public void showSnackBar(String msg) {

    }

    @Override
    public void showAlert(String msg) {

    }
}
