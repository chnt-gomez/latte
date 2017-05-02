package com.go.kchin.view.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.go.kchin.R;
import com.go.kchin.adapters.QuickSaleAdapter;
import com.go.kchin.interfaces.LoaderRequiredOps;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.PDFBuilder;
import com.go.kchin.util.utilities.Dialogs;
import com.go.kchin.util.utilities.Loader;
import com.go.kchin.util.utilities.NFormatter;
import com.itextpdf.text.DocumentException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;

/**
 * Created by MAV1GA on 24/01/2017.
 */

public class QuickReportFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener{

    @BindView(R.id.txt_sale_total)
    TextView txtSaleTotal;

    @BindView(R.id.txt_total_purchases)
    TextView txtPurchases;

    @BindView(R.id.txt_total_earnings)
    TextView txtTotalEarnings;

    @BindView(R.id.txt_sale_ticket)
    TextView txtSaleTickets;

    @BindView(R.id.btn_date)
    Button btnDate;


    private File pdfFile;
    private DateTime currentDateTime = null;

    private MainMVP.QuickReportPresenterOps mReportPresenter;
    private QuickSaleAdapter adapter;

    public static QuickReportFragment newInstance(){
        QuickReportFragment fragment = new QuickReportFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_quick_report);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        super.init();
        currentDateTime = DateTime.now();
        adapter = new QuickSaleAdapter();
        reload();
    }

    @Override
    public void onLoad() {

        super.onLoad();


        adapter.setTotalSales(NFormatter.floatToStringAsPrice(
                mReportPresenter.getDaySaleTotal(currentDateTime),false));
        adapter.setTotalPurchases(NFormatter.floatToStringAsPrice(
                mReportPresenter.getDayPurchasesTotal(currentDateTime),false));
        adapter.setTotalEarnings(NFormatter.floatToStringAsPrice(
                mReportPresenter.getNetEarnings(currentDateTime), false));
        adapter.setTicketIds(mReportPresenter.getRecordedTicketsIdRange(currentDateTime));
        adapter.setDate(currentDateTime);

    }

    @Override
    public void onDoneLoading() {
        super.onDoneLoading();
        updateView();
    }

    @OnClick(R.id.btn_date)
    public void onDatePickClick(View v){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        new DatePickerDialog(getActivity(), this, year, month, day).show();
    }

    @OnClick(R.id.btn_see_details)
    public void moveToDetail(View view){
       mPresenter.moveToFragment(DetailedQuickReport.newInstance(currentDateTime));
    }

    private void updateView() {
        txtSaleTotal.setText(adapter.getTotalSales());
        txtPurchases.setText(adapter.getTotalPurchases());
        btnDate.setText(adapter.getFormattedDate());
        txtSaleTickets.setText(adapter.getFormattedTickets());
        txtTotalEarnings.setText(adapter.getTotalEarnings());
    }

    @OnClick(R.id.btn_save_pdf)
    public void onPdfClick(View v){

        buildPdf();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void buildPdf() {
        Loader loader = new Loader(new LoaderRequiredOps() {

            @Override
            public void onPreLoad() {
                Dialogs.buildLoadingDialog(getContext(), getString(R.string.building_pdf)).
                        show();
            }

            @Override
            public void onLoad() {

                try {
                    pdfFile = PDFBuilder.buildSalesReport(getContext(), buildPdfName(),
                            adapter, getResources(), mReportPresenter, currentDateTime);
                } catch (DocumentException e) {

                } catch (IOException e) {

                }
            }

            @Override
            public void onDoneLoading() {
                Dialogs.dismiss();
                if (pdfFile != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri fileUri = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext()
                    .getPackageName()+".provider", pdfFile);
                    intent.setDataAndType(fileUri, "application/pdf");
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);
                }
            }

            @Override
            public void onSearch(String query) {

            }
        });

        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            loader.execute();
        }else {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onPermissionGranted() {
        super.onPermissionGranted();
    }

    private String buildPdfName() {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd_MM_yyyy_HH_mm");
        return dtf.print(currentDateTime)+getString(R.string.sale_report)+(".pdf");
    }

    private void reload() {
        final Loader loader = new Loader(this);
        loader.execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mReportPresenter = (MainMVP.QuickReportPresenterOps)context;
    }

    @Override
    public void onDetach() {
        mReportPresenter = null;
        super.onDetach();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        DateTime date = new DateTime(year, monthOfYear+1, dayOfMonth,
                DateTime.now().getHourOfDay(), DateTime.now().getMinuteOfHour());
        reloadWithDate(date);
    }

    private void reloadWithDate(DateTime date) {
        currentDateTime = date;
        if (currentDateTime.getMillis() > DateTime.now().getMillis()){
            showMessage(R.string.cannot_travel_in_time);
            currentDateTime = DateTime.now();
        }
        reload();
    }

}
