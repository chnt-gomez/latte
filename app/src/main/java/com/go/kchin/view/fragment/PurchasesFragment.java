package com.go.kchin.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.adapters.PurchaseListAdapter;
import com.go.kchin.interfaces.LoaderRequiredOps;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.model.MailBuilder;
import com.go.kchin.model.PDFBuilder;
import com.go.kchin.util.dialog.Dialogs;
import com.go.kchin.util.dialog.loader.Loader;
import com.itextpdf.text.DocumentException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by MAV1GA on 16/02/2017.
 */

public class PurchasesFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    private MainMVP.PurchasesPresenterOps mPurchasesPresenter;
    private PurchaseListAdapter adapter;
    private File pdfFIle;

    @BindView(R.id.lv_inventory)
    ListView listView;


    public static PurchasesFragment newIstance(){
        PurchasesFragment fragment = new PurchasesFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_purchases);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.btn_send)
    public void onSendClick(View v) {
        buildPdf();
    }

    private void sendToMail() throws IOException, DocumentException {
        final Intent shareIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.new_purchase_order));
        shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                Html.fromHtml(MailBuilder.buildPurchaseOrder(null, "store", "10/25/2017",
                        getResources())));
        shareIntent.putExtra(
                Intent.EXTRA_STREAM,
                Uri.fromFile(PDFBuilder.buildPurchaseOrder(getContext(), "new.pdf",
                        mPurchasesPresenter.getAllDepletedArticles(), getResources())));
        startActivity(shareIntent);
    }

    private void buildPdf(){
        Loader loader = new Loader(new LoaderRequiredOps() {

            @Override
            public void onPreLoad() {
                Dialogs.buildLoadingDialog(getContext(), getString(R.string.building_pdf)).
                        show();
            }

            @Override
            public void onLoad() {

                try {
                     pdfFIle = PDFBuilder.buildPurchaseOrder(getContext(), buildPdfName(),
                            mPurchasesPresenter.getAllDepletedArticles(), getResources());
                }catch (DocumentException e){

                }catch (IOException e){

                }
            }

            @Override
            public void onDoneLoading() {
                Dialogs.dismiss();
                if (pdfFIle != null){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(pdfFIle), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }
            }

            @Override
            public void onSearch(String query) {

            }
        });
        loader.execute();

    }

    private String buildPdfName() {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd_MM_yyyy_HH_mm");
        DateTime dateTime = DateTime.now();
        return dtf.print(dateTime)+getString(R.string.purchase)+(".pdf");
    }

    @Override
    protected void init() {
        super.init();
    }

    private void reload(@Nullable String query){
        final Loader loader = new Loader(this);
        listView.setOnItemClickListener(this);
        loader.execute(query);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPurchasesPresenter = (MainMVP.PurchasesPresenterOps) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        reload(null);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        adapter = new PurchaseListAdapter(getContext(), R.layout.row_product_item,
                mPurchasesPresenter.getAllDepletedArticles());
    }

    @Override
    public void onDoneLoading() {
        super.onDoneLoading();
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Dialogs.newFloatDialog(getContext(), getString(R.string.buy_more), null, new RequiredDialogOps.NewFloatOps() {
            @Override
            public void onNewFloat(float arg) {

                mPurchasesPresenter.purchase(adapter.getItem(position), arg);
            }
        }).show();
    }

    @Override
    public void onOperationSuccesfull(String message) {
        super.onOperationSuccesfull(message);
        reload(null);
    }

}
