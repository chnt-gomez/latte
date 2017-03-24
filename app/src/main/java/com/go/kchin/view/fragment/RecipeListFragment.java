package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.adapters.RecipeListAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.util.utilities.Dialogs;
import com.go.kchin.util.utilities.Loader;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;

/**
 * Created by MAV1GA on 26/01/2017.
 */

public class RecipeListFragment extends BaseFragment implements AdapterView.OnItemClickListener{



    private static final String PRODUCT_ID = "product_id";
    private RecipeListAdapter adapter;
    private MainMVP.ProductPresenterOps mProductPresenter;

    @BindView(R.id.lv_inventory)
    ListView listView;

    @BindView(R.id.btn_add)
    FloatingActionButton btnAdd;

    public static RecipeListFragment newInstance(long productId){
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(LAYOUT_RES_ID, R.layout.fragment_product_recipe);
        arguments.putLong(PRODUCT_ID, productId);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void init() {
        super.init();
        listView.setOnItemClickListener(this);
        listView.setEmptyView(view.findViewById(android.R.id.empty));
        mPresenter.setActivityTitle(getString(R.string.product_recipe));
        reload();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mProductPresenter = (MainMVP.ProductPresenterOps) context;
    }

    @Override
    public void onDetach() {
        mProductPresenter = null;
        super.onDetach();
    }

    private void reload() {
        Loader loader = new Loader(this);
        loader.execute();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        adapter = new RecipeListAdapter(getContext(), R.layout.row_material_item,
                mProductPresenter.getRecipe(getArguments().getLong(PRODUCT_ID)));
    }



    @OnClick(R.id.btn_add)
    public void addToRecipe(View view){
        mPresenter.moveToFragment(BuildRecipeFragment.newInstance(getArguments().getLong(PRODUCT_ID)));
    }

    @Override
    protected void onOperationResultClick(long rowId) {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onOperationSuccesfull(String message, @Nullable long rowId) {
        super.onOperationSuccesfull(message, rowId);
    }

    @Override
    public void onDoneLoading() {
        super.onDoneLoading();
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Dialogs.newFloatDialog(getContext(), getString(R.string.set_new_amount),
                getString(R.string.amount_recipe_summary), new RequiredDialogOps.NewFloatOps() {
            @Override
            public void onNewFloat(float arg) {
                mProductPresenter.setRecipeMaterialAmount(adapter.getItem(position).getId(), arg);
                reload();
            }
        }).show();
    }

    @Override
    public void onShowTutorial() {
        super.onShowTutorial();
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity());
        if(view.findViewById(R.id.txt_material_name) != null){
            sequence.addSequenceItem(buildView(R.id.txt_material_name,
                    R.string.tutorials_recipes_1));
            sequence.addSequenceItem(buildView(R.id.txt_material_amount, "Puedes especificar la cantidad de Material usado para " +
                    "que tus inventarios se ajusten automáticamente tras Vender, Producir o Comprar más del Producto"));
        }
        sequence.addSequenceItem(buildView(R.id.btn_add, "Puedes añadir más materiales a esta receta."));
        sequence.start();
    }
}
