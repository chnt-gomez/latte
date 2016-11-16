package com.go.kchin.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.go.kchin.R;
import com.go.kchin.interfaces.FragmentNavigationService;
import com.go.kchin.interfaces.InventoryService;
import com.go.kchin.models.Department;

/**
 * Created by MAV1GA on 14/11/2016.
 */

public class DepartmentDetailFragment extends Fragment{

    private static final String DEPARTMENT_ID = "departmentId";
    private long departmentId;
    private View view;
    private Department department;
    private EditText edtDepartment;
    private InventoryService inventoryService;
    private FragmentNavigationService navigationService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            departmentId = getArguments().getLong(DEPARTMENT_ID);
        }
    }

    public static DepartmentDetailFragment newInstance(long materialId){
        DepartmentDetailFragment fragment = new DepartmentDetailFragment();
        Bundle args = new Bundle();
        args.putLong(DEPARTMENT_ID, materialId);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_department_detail, null);
        init();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.inventoryService = (InventoryService)context;
        this.navigationService = (FragmentNavigationService)context;
    }

    private void init() {
        department = inventoryService.getDepartment(departmentId);
        edtDepartment = (EditText)view.findViewById(R.id.edt_department_name);
        edtDepartment.setText(department.getDepartmentName());
    }
}
