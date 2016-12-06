package com.go.kchin.interfaces;

import android.support.v4.app.Fragment;

/**
 * Created by MAV1GA on 09/11/2016.
 */

public interface FragmentNavigationService {

    void moveToFragment(Fragment fragment, boolean addToBackStackTrace);
    void setActionBarTitle(String title);
    void hideActionBar();
    void showActionBar();

}
