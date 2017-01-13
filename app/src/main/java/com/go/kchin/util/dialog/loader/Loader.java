package com.go.kchin.util.dialog.loader;

import android.os.AsyncTask;
import com.go.kchin.interfaces.LoaderRequiredOps;

/**
 * Created by MAV1GA on 09/01/2017.
 */

public class Loader extends AsyncTask<Void, Void, Void> {

    private LoaderRequiredOps presenter;

    public Loader(LoaderRequiredOps presenter){
       this.presenter = presenter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        presenter.onPreLoad();
    }

    @Override
    protected Void doInBackground(Void... params) {
        presenter.onLoad();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        presenter.onDoneLoading();
        presenter = null;
    }
}
