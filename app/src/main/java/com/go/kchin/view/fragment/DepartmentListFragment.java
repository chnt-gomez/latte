package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.Button;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.adapters.DepartmentListAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.model.database.Department;
import com.go.kchin.util.dialog.Dialogs;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MAV1GA on 10/01/2017.
 */

public class DepartmentListFragment extends BaseFragment implements
        RequiredDialogOps.RequiredNewDepartmentOps{

    @BindView(R.id.btn_add)FloatingActionButton btnAdd;

    private ListView listView;
    private DepartmentListAdapter adapter;
    private MainMVP.DepartmentsPresenterOps mDepartmentsPresenter;

    public static DepartmentListFragment newInstance(){
        DepartmentListFragment fragment = new DepartmentListFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_inventory);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDepartmentsPresenter = (MainMVP.DepartmentsPresenterOps)context;
    }

    @OnClick(R.id.btn_add)
    public void addDepartment(){
        Dialogs.newDepartmentDialog(getContext(), getResources().getString(R.string.new_department),
                this).show();
    }

    @Override
    protected void init() {
        super.init();
        listView = (ListView)view.findViewById(R.id.lv_inventory);
        reload();
    }

    private void reload(){
        adapter = new DepartmentListAdapter(getContext(), R.layout.row_despartment_item,
                mDepartmentsPresenter.getAllDepartments());
        if (listView != null) {
            listView.setAdapter(adapter);
        }else{
            throw new RuntimeException("Either adapter or listview is not correctly initialized");
        }
    }

    @Override
    protected void onOperationResultClick(long rowId) {
        //seeDetail(rowId);
    }

    @Override
    public void onNewDepartment(Department department) {
        mDepartmentsPresenter.addDepartment(department);
        reload();
    }
}
