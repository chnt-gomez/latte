package com.go.kchin.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.adapters.LowInventoryAdapter;
import com.go.kchin.interfaces.LoaderRequiredOps;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.model.MailBuilder;
import com.go.kchin.model.PDFBuilder;
import com.go.kchin.model.SimplePurchase;
import com.go.kchin.util.utilities.Dialogs;
import com.go.kchin.util.utilities.Loader;
import com.itextpdf.text.DocumentException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;


/**
 * Created by MAV1GA on 16/02/2017.
 */

public class LowInventoryFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    private MainMVP.LowInventoryOps mPurchasesPresenter;
    private LowInventoryAdapter adapter;
    private File pdfFIle;

    @BindView(R.id.lv_inventory)
    ListView listView;


    public static LowInventoryFragment newIstance(){
        LowInventoryFragment fragment = new LowInventoryFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_purchases);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onShowTutorial() {
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity());
        if (view.findViewById(R.id.txt_product_name) != null){
            sequence.addSequenceItem(buildView(R.id.txt_product_name,
                    R.string.tutorials_low_inv_1));
            sequence.addSequenceItem(buildView(R.id.txt_product_sale_price,
                    R.string.tutorials_low_inv_2));
            sequence.addSequenceItem(buildView(R.id.txt_product_name,
                    R.string.tutorials_low_inv_3));
        }
        sequence.addSequenceItem(buildView(R.id.btn_send,
                R.string.tutorials_low_inv_4));
        sequence.start();
    }



    @OnClick(R.id.btn_send)
    public void onSendClick(View v) {
        buildPdf();
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
        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            loader.execute();
        else
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

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
        listView.setEmptyView(view.findViewById(android.R.id.empty));
        loader.execute(query);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPurchasesPresenter = (MainMVP.LowInventoryOps) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        reload(null);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        adapter = new LowInventoryAdapter(getContext(), R.layout.row_product_item,
                mPurchasesPresenter.getAllDepletedArticles());
    }

    @Override
    public void onDoneLoading() {
        super.onDoneLoading();
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Dialogs.newPurchaseDialog(getContext(), getString(R.string.buy_more), null, new RequiredDialogOps.RequiredNewPurchaseOps() {
            @Override
            public void onNewPurchase(SimplePurchase purchase) {
                mPurchasesPresenter.purchase(adapter.getItem(position), purchase.getPurchasedItems(),
                        purchase.getPurchaseAmount());
            }
        }).show();
    }

    @Override
    public void onOperationSuccesfull(String message) {
        super.onOperationSuccesfull(message);
        reload(null);
    }


}
