package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.adapters.DepartmentListAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.util.dialog.loader.Loader;

import butterknife.BindView;
import butterknife.OnItemClick;

/**
 * Created by MAV1GA on 26/01/2017.
 */

public class PickDepartmentFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    private static final String PRODUCT_ID = "product_id";
    @BindView(R.id.lv_inventory)
    ListView listView;

    private DepartmentListAdapter adapter;
    private MainMVP.ProductPresenterOps mProductPresenter;

    public static PickDepartmentFragment newInstance(long productId){
        PickDepartmentFragment fragment = new PickDepartmentFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_inventory);
        args.putLong(PRODUCT_ID, productId);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mProductPresenter = (MainMVP.ProductPresenterOps) context;
    }

    @Override
    public void onDetach() {
        mProductPresenter = null;
        super.onDetach();
    }

    @Override
    protected void init() {
        super.init();
        reload();
        listView.setOnItemClickListener(this);
    }

    private void reload() {
        Loader loader = new Loader(this);
        loader.execute();
    }

    @Override
    public void onPreLoad() {
        super.onPreLoad();
        adapter = new DepartmentListAdapter(getContext(), R.layout.row_despartment_item,
                mProductPresenter.getAllDepartments());
    }

    @Override
    public void onDoneLoading() {
        super.onDoneLoading();
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mProductPresenter.pickDepartment(
                getArguments().getLong(PRODUCT_ID), adapter.getItem(position));
    }
}
