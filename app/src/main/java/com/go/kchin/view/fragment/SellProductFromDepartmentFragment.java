package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.go.kchin.R;
import com.go.kchin.adapters.ProductListAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.model.database.Product;
import com.go.kchin.util.utilities.Dialogs;
import com.go.kchin.util.utilities.Loader;
import butterknife.BindView;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;


/**
 * Created by MAV1GA on 18/01/2017.
 */

public class SellProductFromDepartmentFragment extends BaseFragment implements
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private ProductListAdapter productListAdapter;
    private MainMVP.SalesPresenterOps mSalesPresenter;

    private static final String DEPARTMENT_GROUP = "department_group";
    private long departmentId;

    @BindView(R.id.lv_inventory)
    ListView listView;

    public static SellProductFromDepartmentFragment newInstance(long departmentId){
        SellProductFromDepartmentFragment fragment = new SellProductFromDepartmentFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(LAYOUT_RES_ID, R.layout.fragment_sell);
        arguments.putLong(DEPARTMENT_GROUP, departmentId );
        fragment.setArguments(arguments);
        return fragment;
    }

    private void reload(@Nullable String query){
        final Loader loader = new Loader(this);
        loader.execute(query);
    }

    @Override
    public void onShowTutorial() {
        super.onShowTutorial();
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity());


        if(mSalesPresenter.isShowingTicket()){
            sequence.addSequenceItem(buildSquareView(mSalesPresenter.getSlidingPanelView(), "Para eliminar un producto del ticket " +
                    "manten presionado el elemento."));
            sequence.start();
            return;
        }

        if (view.findViewById(R.id.txt_product_name) != null) {
            sequence.addSequenceItem(buildView(R.id.txt_product_name,
                    "Los productos aparecen enlistados aqui. Para agregarlos a la venta simplemente" +
                            " tócalos."));
            sequence.addSequenceItem(buildView(R.id.txt_product_name,
                    "Si deseas agregar más de uno, mantén presionado el producto para añadir la cantidad" +
                            " que desees"));
        }
        sequence.addSequenceItem(buildSquareView(mSalesPresenter.getSlidingPanelView(),
                "Cuando agregues un producto, el total de la venta se acumulará. Puedes ver todos los productos " +
                        "que agregas si tocas la flecha."));
        sequence.addSequenceItem(buildSquareView(mSalesPresenter.getSlidingPanelButton(),
                "Cuando la venta esté completa, usa el botón de aplicar para registrarla."));
        sequence.start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSalesPresenter = (MainMVP.SalesPresenterOps)context;
    }



    @Override
    protected void init() {
        super.init();
        listView.setEmptyView(view.findViewById(android.R.id.empty));
        departmentId = getArguments().getLong(DEPARTMENT_GROUP);
        reload(null);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

    }

    @Override
    public void onLoad() {
        productListAdapter = new ProductListAdapter(getContext(), R.layout.row_product_item,
                mSalesPresenter.getProductsInDepartment(departmentId));
    }

    @Override
    public void onDoneLoading() {
        super.onDoneLoading();
       listView.setAdapter(productListAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        addToCurrentSale(productListAdapter.getItem(position), 1.0f);
    }
    private void addToCurrentSale(Product item, float productAmount) {
        mSalesPresenter.addToCurrentSale(item, productAmount);
    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {
        final int position = i;
        Dialogs.newFloatDialog(getContext(), getString(R.string.add_many), null, new RequiredDialogOps.NewFloatOps() {
            @Override
            public void onNewFloat(float arg) {
                addToCurrentSale(productListAdapter.getItem(position), arg);
            }
        }).show();
        return true;
    }

}
