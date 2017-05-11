package com.go.kchin.presenter.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.go.kchin.R;
import com.go.kchin.adapters.SaleAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.model.database.Department;
import com.go.kchin.model.database.Product;
import com.go.kchin.model.database.Sale;
import com.go.kchin.util.utilities.Dialogs;
import com.go.kchin.util.utilities.NFormatter;
import com.go.kchin.view.fragment.DepartmentGridFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MAV1GA on 18/01/2017.
 */

public class SaleActivity extends BaseActivity implements MainMVP.SalesPresenterOps, SearchView.OnQueryTextListener,
        RequiredDialogOps.RequiredQuickSaleOps{

    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slideLayout;

    @BindView(R.id.txt_sale_total)
    TextView txtTotal;

    @BindView(R.id.lv_sale)
    ListView saleListView;

    @BindView(R.id.btn_apply_sale)
    Button btnApplySale;

    private SaleAdapter saleAdapter;

    @Override
    public void onOperationSuccess(String message) {
        mView.showMessage(message);
        saleAdapter.clear();
        txtTotal.setText(NFormatter.floatToStringAsPrice(saleAdapter.getTotal(), false));
        validateApplyButton();
    }

    @Override
    public void onOperationSuccess(int resource) {
        super.onOperationSuccess(resource);
        saleAdapter.clear();
        txtTotal.setText(NFormatter.floatToStringAsPrice(saleAdapter.getTotal(), false));
        validateApplyButton();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sale_activity);
        ButterKnife.bind(this);
        setTitle(R.string.title_sale);

        slideLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED){
                    txtTotal.setCompoundDrawablesWithIntrinsicBounds
                            (R.drawable.ic_attach_money_white_24dp, 0, R.drawable.ic_keyboard_arrow_down_white_24dp, 0);
                }
                if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED){
                    txtTotal.setCompoundDrawablesWithIntrinsicBounds
                            (R.drawable.ic_attach_money_white_24dp, 0, R.drawable.ic_keyboard_arrow_up_white_24dp, 0);
                }
            }
        });
        saleAdapter = new SaleAdapter(this, R.layout.row_sell_item, new ArrayList<Sale>());
        saleListView.setAdapter(saleAdapter);
        saleListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                saleAdapter.remove(saleAdapter.getItem(position));
                txtTotal.setText(NFormatter.floatToStringAsPrice(saleAdapter.getTotal(), false));
                validateApplyButton();
                return true;
            }
        });
        txtTotal.setText(NFormatter.floatToStringAsPrice(saleAdapter.getTotal(), false));
        validateApplyButton();

    }

    @Override
    protected void init() {
        super.init();
        attachFragment(DepartmentGridFragment.newInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu_with_add, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add){
            Dialogs.newQuickSaleDialog(this, getString(R.string.add_temp_product), this).show();
            return true;
        }
        if (item.getItemId() == R.id.action_calculator){
            Dialogs.newChangeCalculatorDialog(this, getString(R.string.change_calculator), null, saleAdapter.getTotal(),null)
            .show();
            return true;
        }
        return false;
    }

    @OnClick(R.id.btn_apply_sale)
    public void onApplyClick(View v){
        applyCurrentSale(saleAdapter.getAll());
    }

    @Override
    public void applyCurrentSale(List<Sale> currentSale) {

        mModel.applySale(currentSale);
    }

    @Override
    public List<Product> getProducts() {
        return mModel.getAllProducts();
    }

    @Override
    public List<Product> getAllProducts(String query) {
        return mModel.getProducts(query);
    }

    @Override
    public List<Department> getDepartments() {
        return mModel.getAllDepartments();
    }

    @Override
    public List<Department> getDepartments(String query) {
        return mModel.getDepartments(query);
    }

    @Override
    public List<Product> getProductsInDepartment(long departmentId) {
        return mModel.getProductsFromDepartment(departmentId);
    }

    @Override
    public List<Product> getProductsInDepartment(long departmentId, String query) {
        return mModel.getProductsFromDepartment(departmentId, query);
    }

    @Override
    public List<Product> getProducts(long departmentId, String query) {
        return mModel.getProductsFromDepartment(departmentId, query);
    }

    @Override
    public View getSlidingPanelView() {
        return findViewById(R.id.txt_sale_total);
    }

    @Override
    public View getSlidingPanelButton() {
        return findViewById(R.id.btn_apply_sale);
    }

    @Override
    public boolean isShowingTicket() {
        return slideLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED;
    }

    @Override
    public void onBackPressed() {
        if (slideLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED)
            slideLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        else
            super.onBackPressed();
    }

    @Override
    public void addToCurrentSale(Product item, float amount) {
        if (amount <= 0){
            onOperationError(getStringResource(R.string.cannot_add_negative_sale));
            return;
        }
        Sale sale = new Sale();
        sale.product = item;
        sale.productAmount = amount;
        sale.saleConcept = item.productName;
        sale.saleTotal = item.productSellPrice * amount;
        saleAdapter.add(sale);
        txtTotal.setText(NFormatter.floatToStringAsPrice(saleAdapter.getTotal(), false));
        validateApplyButton();
    }

    private void validateApplyButton() {
        if (saleAdapter.getCount() > 0)
            btnApplySale.setEnabled(true);
        else
            btnApplySale.setEnabled(false);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mView.search(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() >= 3 && newText.length() % 3 == 0){
            mView.search(newText);
        }

        if(newText.length() == 0){
            mView.search(null);
        }
        return true;
    }

    @Override
    public void onQuickSale(Product product) {
        product.productName += getString(R.string.temporal_product_setting);
        addToCurrentSale(product, 1);
    }
}
