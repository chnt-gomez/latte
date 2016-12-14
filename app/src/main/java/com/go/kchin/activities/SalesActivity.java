package com.go.kchin.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;

import com.go.kchin.R;
import com.go.kchin.fragments.QuickSaleFragment;
import com.go.kchin.models.Sale;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAV1GA on 22/11/2016.
 */

public class SalesActivity extends KchinSuperActivity{

    private List<Sale> currentSale;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    protected void init(){
        super.init();
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_holder,
                QuickSaleFragment.newInstance()).commit();
        currentSale = new ArrayList<>();
    }

    @Override
    public List<Sale> getCurrentSale() {
        return currentSale;
    }

    @Override
    public void addToSale(Sale sale) {
        currentSale.add(sale);
    }

    @Override
    public void undoWithProductId(long productId) {
        for (int i=0; i<currentSale.size(); i++){
            if (currentSale.get(i).getObjectId() == productId && currentSale.get(i).isProduct()){
                currentSale.remove(i);
                break;
            }
        }
    }
}
