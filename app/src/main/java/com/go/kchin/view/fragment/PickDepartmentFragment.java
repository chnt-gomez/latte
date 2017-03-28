package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.adapters.DepartmentListAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.model.database.Department;
import com.go.kchin.util.utilities.Dialogs;
import com.go.kchin.util.utilities.Loader;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

/**
 * Created by MAV1GA on 26/01/2017.
 */

public class PickDepartmentFragment extends BaseFragment implements AdapterView.OnItemClickListener,
        RequiredDialogOps.RequiredNewDepartmentOps{

    private static final String PRODUCT_ID = "product_id";
    @BindView(R.id.lv_inventory)
    ListView listView;


    private DepartmentListAdapter adapter;
    private MainMVP.ProductPresenterOps mProductPresenter;

    public static PickDepartmentFragment newInstance(long productId){
        PickDepartmentFragment fragment = new PickDepartmentFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_department_list);
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
    public void onResume() {
        super.onResume();
        mPresenter.setActivityTitle(getString(R.string.assign_department));
    }

    @Override
    protected void init() {
        super.init();
        listView.setEmptyView(view.findViewById(android.R.id.empty));
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
    public void onOperationSuccesfull(String message) {
        super.onOperationSuccesfull(message);
        reload();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mProductPresenter.pickDepartment(
                getArguments().getLong(PRODUCT_ID), adapter.getItem(position));
    }

    @OnClick (R.id.btn_add)
    public void onAddButtonClick(View view){
        Dialogs.newDepartmentDialog(getContext(), getResources().getString(R.string.new_department),
                this).show();
    }

    @Override
    public void onNewDepartment(Department department) {
        mProductPresenter.addDepartment(department);
    }

    @Override
    public void onShowTutorial() {
        new MaterialShowcaseView.Builder(getActivity())
                .setTarget(view.findViewById(R.id.btn_add))
                .setContentText(getString(R.string.add_more_departments))
                .setDismissOnTouch(true)
                .setMaskColour(ContextCompat.getColor(getContext(),
                        R.color.colorDarkGrayBlue))
                .singleUse("create_a_department")
                .build().show(getActivity());
    }
}
