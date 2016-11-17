package com.go.kchin.fragments;

import android.os.Bundle;
import android.widget.EditText;

import com.go.kchin.R;
import com.go.kchin.models.Department;

/**
 * Created by MAV1GA on 14/11/2016.
 */

public class DepartmentDetailFragment extends InventoryDetailFragment {

    private Department department;
    private EditText edtDepartment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            objectId = getArguments().getLong(OBJECT_ID);
        }
    }

    public static DepartmentDetailFragment newInstance(long materialId){
        DepartmentDetailFragment fragment = new DepartmentDetailFragment();
        Bundle args = new Bundle();
        args.putLong(OBJECT_ID, materialId);
        args.putInt(LAYOUT_RES, R.layout.fragment_department_detail);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    protected void init() {
        super.init();
        department = inventoryService.getDepartment(objectId);
        edtDepartment = (EditText) findViewById(R.id.edt_department_name);
        edtDepartment.setText(department.getDepartmentName());
        addTextWatcher(edtDepartment);
    }

    @Override
    protected void save() {
        String newDepartmentName = edtDepartment.getText().toString();
        department.setDepartmentName(newDepartmentName);
        inventoryService.updateDepartment(objectId, department);
        super.save();
    }

    @Override
    protected void undo() {
        edtDepartment.setText(department.getDepartmentName());
        super.undo();
    }
}
