package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.go.kchin.R;
import com.go.kchin.adapters.DepartmentGridAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Department;
import com.go.kchin.util.utilities.Loader;

import butterknife.BindView;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;

/**
 * Created by vicente on 19/02/17.
 */

public class DepartmentGridFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    @BindView(R.id.grid)
    GridView gridView;

    private DepartmentGridAdapter adapter;
    private MainMVP.SalesPresenterOps mSalesPresenter;

    public static DepartmentGridFragment newInstance() {
        DepartmentGridFragment fragment = new DepartmentGridFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_department_sell);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onShowTutorial() {

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity());
        if(mSalesPresenter.isShowingTicket()){
            sequence.addSequenceItem(buildSquareView(mSalesPresenter.getSlidingPanelView(),
                    R.string.tutorials_department_sale_1));
            sequence.start();
            return;
        }
        sequence.addSequenceItem(buildView(R.id.txt_department_name,
                R.string.tutorials_department_sale_2));
        sequence.addSequenceItem(buildSquareView(mSalesPresenter.getSlidingPanelView(),
                R.string.tutorials_department_sale_3));
        sequence.addSequenceItem(buildSquareView(mSalesPresenter.getSlidingPanelButton(),
                R.string.tutorials_department_sale_4));
        sequence.start();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSalesPresenter = (MainMVP.SalesPresenterOps) context;
    }

    @Override
    public void onDetach() {
        mSalesPresenter = null;
        super.onDetach();

    }

    @Override
    protected void init() {
        super.init();
        reload();
        gridView.setOnItemClickListener(this);

    }

    private void reload() {
        Loader loader = new Loader(this);
        loader.execute();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        adapter = new DepartmentGridAdapter(getActivity(), R.layout.gird_department_item_layout,
                mSalesPresenter.getDepartments());
        //Create the fake departament
        Department fakeDepartment = new Department();
        fakeDepartment.departmentName = getString(R.string.no_department);
        fakeDepartment.setId((long) -1);
        adapter.add(fakeDepartment);
    }

    @Override
    public void onDoneLoading() {
        super.onDoneLoading();
        gridView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long departmentId = adapter.getItem(position).getId();
        mPresenter.moveToFragment(SellProductFromDepartmentFragment.newInstance(departmentId));
    }
}
