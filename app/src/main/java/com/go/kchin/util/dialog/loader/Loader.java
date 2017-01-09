package com.go.kchin.util.dialog.loader;

import android.content.Context;
import android.os.AsyncTask;

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

    public Loader(Context context, LoaderRequiredOps presenter){
        if (presenter == null){
            throw new RuntimeException("Presenter must implement LoaderRequiredOps or use a new" +
                    " Interface as constructor argument");
        }else{
            this.presenter = presenter;
            this.context = context;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Dialogs.buildLoadingDialog(context, "Loading products...").show();
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
