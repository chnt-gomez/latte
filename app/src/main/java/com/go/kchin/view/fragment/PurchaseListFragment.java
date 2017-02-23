package com.go.kchin.view.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import com.go.kchin.R;
import com.go.kchin.adapters.PurchasesAdapter;
import com.go.kchin.interfaces.LoaderRequiredOps;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.PDFBuilder;
import com.go.kchin.util.dialog.Dialogs;
import com.go.kchin.util.dialog.loader.Loader;
import com.go.kchin.util.dialog.number.Number;
import com.itextpdf.text.DocumentException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MAV1GA on 21/02/2017.
 */

public class PurchaseListFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener{

    private PurchasesAdapter adapter;
    private MainMVP.PurchasesPresenterOps purchasesPresenter;
    private DateTime currentDateTime = null;
    float totalPurchases = 0;
    File pdfFile;

    @BindView(R.id.lv_inventory)
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static PurchaseListFragment newInstance(){
        PurchaseListFragment fragment = new PurchaseListFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_purchase_list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_date){
            pickDate();
            return true;
        }
        return false;
    }

    private void pickDate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(getActivity(), this, year, month, day).show();
    }

    @Override
    protected void init() {
        super.init();
        listView.setEmptyView(view.findViewById(android.R.id.empty));
        reload();
    }

    private void reload(){
        Loader loader = new Loader(this);
        loader.execute();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        adapter = new PurchasesAdapter(getContext(), R.layout.row_purchase_item,
                purchasesPresenter.getPurchases(currentDateTime));

        totalPurchases = purchasesPresenter.getTotalPurchases(currentDateTime);
    }

    @Override
    public void onDoneLoading() {
        super.onDoneLoading();
        listView.setAdapter(adapter);
        if (currentDateTime == null)
            currentDateTime = DateTime.now();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd, MMMM");

        mPresenter.setActivityTitle(Number.floatToStringAsPrice(totalPurchases, true)
                + " - " +dtf.print(currentDateTime)  );
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.purchasesPresenter = (MainMVP.PurchasesPresenterOps)context;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        currentDateTime = new DateTime(year, monthOfYear+1, dayOfMonth,
                DateTime.now().getHourOfDay(), DateTime.now().getMinuteOfHour());
        reload();
    }

    @OnClick(R.id.btn_pdf)
    public void onPdfClick(View v){
        generatePdf();
    }

    private void generatePdf() {
        Loader loader = new Loader(new LoaderRequiredOps() {
            @Override
            public void onPreLoad() {
                Dialogs.buildLoadingDialog(getContext(), getString(R.string.building_pdf)).
                        show();
            }

            @Override
            public void onLoad() {
                try {
                    pdfFile = PDFBuilder.buildPurchasesReport(getContext(), buildPdfName(),
                            adapter, getResources(), purchasesPresenter, currentDateTime);
                }catch (DocumentException e){

                }catch (IOException e){

                }
            }

            @Override
            public void onDoneLoading() {
                Dialogs.dismiss();
                if (pdfFile != null){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(pdfFile), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }
            }

            @Override
            public void onSearch(String query) {

            }
        });
        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            loader.execute();
        else
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
    }

    private String buildPdfName(){
        if (currentDateTime == null){
            currentDateTime = DateTime.now();
        }
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd_MM_yyyy_HH_mm");
        return dtf.print(currentDateTime)+getString(R.string.purchases)+(".pdf");
    }
}
