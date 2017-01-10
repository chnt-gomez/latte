package com.go.kchin.presenter.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Department;
import com.go.kchin.view.fragment.DepartmentListFragment;

import java.util.List;

/**
 * Created by MAV1GA on 10/01/2017.
 */

public class DepartmentsActivity extends BaseActivity implements MainMVP.DepartmentsPresenterOps {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        attachFragment(DepartmentListFragment.newInstance());
    }

    @Override
    public List<Department> getAllDepartments() {
        return mModel.getAllDepartments();
    }

    @Override
    public Department getDepartment(long departmentId) {
        return null;
    }

    @Override
    public void addDepartment(Department department) {
        mModel.addDepartment(department);
    }

    @Override
    public void addProductToDepartment(long productId, long departmentId) {

    }
}
