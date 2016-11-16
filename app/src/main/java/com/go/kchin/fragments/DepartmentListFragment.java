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
import com.go.kchin.util.Util;

import java.util.List;

/**
 * Created by MAV1GA on 14/11/2016.
 */

public class DepartmentListFragment extends Fragment implements Util.DepartmentDialogEventListener, View.OnClickListener{

    private ListView listView;
    private View view;
    private FragmentNavigationService navigationService;
    private InventoryService inventoryService;
    private DepartmentListAdapter adapter;
    private FloatingActionButton btnAdd;

    public DepartmentListFragment(){}

    public static DepartmentListFragment newInstance(){
        return new DepartmentListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_department_list, null);
        init();
        return view;
    }

    private void init() {
        List<Department> list = inventoryService.getDepartments();
        adapter = new DepartmentListAdapter(getActivity(), R.layout.row_despartment_item,
                list);
        listView = (ListView)view.findViewById(R.id.lv_department_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                navigationService.moveToFragment(DepartmentDetailFragment.newInstance(adapter.getItem(position).getDepartmentId()));
            }
        });

        btnAdd = (FloatingActionButton)view.findViewById(R.id.btn_add);
        addToClickListener(btnAdd);
    }

    private void addToClickListener(View ... args) {
        for (View v : args){
            v.setOnClickListener(this);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.navigationService = (FragmentNavigationService)context;
        this.inventoryService = (InventoryService)context;
    }

    @Override
    public void returnDepartment(Department department) {
        inventoryService.addDepartment(department);
    }

    @Override
    public void editDepartment(long id, Department department) {
        inventoryService.updateDepartment(id, department);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                Util.newDepartmentDialog("New department", null, getActivity(), getActivity().getLayoutInflater(),
                        this).show();
                break;
        }
    }
}
