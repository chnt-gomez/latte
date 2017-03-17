package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.adapters.DepartmentListAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.model.database.Department;
import com.go.kchin.util.utilities.Dialogs;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;

/**
 * Created by MAV1GA on 10/01/2017.
 */

public class DepartmentListFragment extends BaseFragment implements
        RequiredDialogOps.RequiredNewDepartmentOps, AdapterView.OnItemClickListener{

    @BindView(R.id.btn_add)FloatingActionButton btnAdd;

    private ListView listView;
    private DepartmentListAdapter adapter;
    private MainMVP.DepartmentsPresenterOps mDepartmentsPresenter;

    public static DepartmentListFragment newInstance(){
        DepartmentListFragment fragment = new DepartmentListFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_department_list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDepartmentsPresenter = (MainMVP.DepartmentsPresenterOps)context;
    }

    @Override
    public void onDetach() {
        mDepartmentsPresenter = null;
        super.onDetach();
    }

    @OnClick(R.id.btn_add)
    public void addDepartment(){
        Dialogs.newDepartmentDialog(getContext(), getResources().getString(R.string.new_department),
                this).show();
    }

    @Override
    public void onShowTutorial() {
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity());
        if (view.findViewById(R.id.txt_department_name) != null){
            sequence.addSequenceItem(buildView(R.id.txt_department_name,
                    R.string.tutorials_department_list_1));
            sequence.addSequenceItem(buildView(R.id.txt_department_products_amount,
                    R.string.tutorials_department_list_2));
            sequence.addSequenceItem(buildView(R.id.txt_department_name,
                    R.string.tutorials_department_list_3));
        }
        sequence.addSequenceItem(buildView(R.id.btn_add,
                R.string.tutorials_department_list_4));
        sequence.start();
    }

    @Override
    protected void init() {
        super.init();
        listView = (ListView)view.findViewById(R.id.lv_inventory);
        listView.setEmptyView(view.findViewById(android.R.id.empty));
        listView.setOnItemClickListener(this);
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
    }

    @Override
    public void onOperationSuccesfull(String message) {
        super.onOperationSuccesfull(message);
        reload();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Dialogs.newStringDialog(getContext(), getString(R.string.change_name), null, new RequiredDialogOps.RequiredNewStringDialog() {
            @Override
            public void onNewString(String newString) {
                    mDepartmentsPresenter.updateDepartmentName(adapter.getItem(position).getId(), newString);
            }
        }, adapter.getItem(position).departmentName).show();
    }
}
