package com.go.kchin.util.dialog.loader;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.go.kchin.R;
import com.go.kchin.interfaces.LoaderRequiredOps;
import com.go.kchin.util.dialog.Dialogs;
import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by MAV1GA on 09/01/2017.
 */

public class Loader extends AsyncTask<Void, Integer, List<SugarRecord>> {

    private LoaderRequiredOps presenter;
    private Context context;
    private String loadingMessage;

    public Loader(Context context, LoaderRequiredOps presenter, @Nullable String loadingMessage){
        if (presenter == null){
            throw new RuntimeException("Presenter must implement LoaderRequiredOps or use a new" +
                    " Interface as constructor argument");
        }else{
            this.presenter = presenter;
            this.context = context;
        }
        if (loadingMessage != null){
            this.loadingMessage = loadingMessage;
        }else{
            this.loadingMessage = context.getResources().getString(R.string.loading);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Dialogs.buildLoadingDialog(context, loadingMessage).show();
    }

    @Override
    protected List<SugarRecord> doInBackground(Void... params) {
        presenter.onLoad();
        return null;
    }

    @Override
    protected void onPostExecute(List<SugarRecord> sugarRecords) {
        super.onPostExecute(sugarRecords);
        Dialogs.dismiss();
        presenter = null;
    }

}
