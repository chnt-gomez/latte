package com.go.kchin.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.go.kchin.R;
import com.go.kchin.presenter.activities.QuickReportActivity;
import com.go.kchin.presenter.activities.SaleActivity;
import butterknife.OnClick;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;

/**
 * Created by MAV1GA on 08/02/2017.
 */

public class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_home);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onShowTutorial() {
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity());
        if (view.findViewById(R.id.cv_inventory) != null){


            sequence.addSequenceItem(buildSquareView(R.id.cv_sales,
                    "Aquí puedes llevar a cabo tus ventas", getString(R.string.got_it)));
            sequence.addSequenceItem(buildSquareView(R.id.cv_inventory,
                    "En el inventario puedes crear productos, modificarlos y revisar sus detalles. También " +
                            "están los Materiales y los reportes de los invenrarios bajos.",
                    getString(R.string.got_it)));
            sequence.addSequenceItem(buildSquareView(R.id.cv_reports, "Todas tus ventas realizadas " +
                    "están registradas en los Reportes de Venta. Si lo deseas, también puedes imprimir " +
                    "en formato PDF los reportes", getString(R.string.got_it)));
            sequence.start();
        }

        if (view.findViewById(R.id.btn_see_products) != null){
            //TODO: continuar con los tutoriales para los otros metodos.
        }
    }

    @Override
    protected void init() {
        super.init();
    }

    @OnClick(R.id.btn_sell)
    public void onSellClick(View v){
        mPresenter.moveToActivity(SaleActivity.class, null);
    }

    @OnClick(R.id.cv_sales)
    public void onSalesClick(View v){mPresenter.moveToActivity(SaleActivity.class, null);}

    @OnClick(R.id.cv_reports)
    public void onReportsClick(View v){mPresenter.moveToActivity(QuickReportActivity.class, null);}

    @OnClick(R.id.cv_inventory)
    public void onInventoryClick(View v){
        mPresenter.moveToFragment(InventoryFragment.newInstance());
    }

}
