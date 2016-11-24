package com.go.kchin.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.adapters.DepartmentListAdapter;
import com.go.kchin.interfaces.FragmentNavigationService;
import com.go.kchin.interfaces.InventoryService;
import com.go.kchin.models.Department;
import com.go.kchin.models.Operation;
import com.go.kchin.util.Util;

import java.util.List;

/**
 * Created by MAV1GA on 14/11/2016.
 */

public class DepartmentListFragment extends InventoryListFragment implements Util.DepartmentDialogEventListener,
        View.OnClickListener{

    public DepartmentListFragment(){}

    public static DepartmentListFragment newInstance(){
        return new DepartmentListFragment();
    }

    @Override
    protected void init() {
        super.init();
        List<Department> list = inventoryService.getDepartments();
        DepartmentListAdapter adapter = new DepartmentListAdapter(getActivity(), R.layout.row_despartment_item,
                list);
        listView.setAdapter(adapter);
    }

    @Override
    public Operation returnDepartment(Department department) {
        return inventoryService.addDepartment(department);
    }

    @Override
    public void onResume() {
        super.onResume();
        navigationService.setActionBarTitle("Departments");
    }

    @Override
    public void editDepartment(long id, Department department) {
        inventoryService.updateDepartment(id, department);
    }

    @Override
    protected void add() {
        Util.newDepartmentDialog("New department", null, getActivity(), getActivity().getLayoutInflater(),
                this).show();
    }
}
