package com.go.kachin.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.go.kachin.R;
import com.go.kachin.interfaces.FragmentNavigationService;
import com.go.kachin.interfaces.InventoryService;
import com.go.kachin.models.Material;
import com.go.kachin.util.Util;


public class MaterialDetailFragment extends Fragment {

    private static final String MATERIAL_ID = "material_id";

    // TODO: Rename and change types of parameters
    private long material_id;
    private View view;

    private EditText edtMaterialName, edtMaterialUnit;
    private Button btnMaterialAmount;

    private Material material;

    private InventoryService inventoryService;
    private FragmentNavigationService navigationService;

    public MaterialDetailFragment() {
        // Required empty public constructor
    }

    public static MaterialDetailFragment newInstance(long material_id) {
        MaterialDetailFragment fragment = new MaterialDetailFragment();
        Bundle args = new Bundle();
        args.putLong(MATERIAL_ID, material_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.inventoryService = (InventoryService)context;
        this.navigationService = (FragmentNavigationService)context;
        if (navigationService!= null)
            navigationService.hideActionBar();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            material_id = getArguments().getLong(MATERIAL_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_material_detail, container, false);
        init();
        return view;
    }

    private void init(){
        edtMaterialName = (EditText) view.findViewById(R.id.edt_material_name);
        edtMaterialUnit = (EditText) view.findViewById(R.id.edt_material_unit);
        btnMaterialAmount = (Button) view.findViewById(R.id.btn_material_amount);

        material = inventoryService.getMaterial(material_id);

        edtMaterialName.setText(material.getMaterialName());
        edtMaterialUnit.setText(material.getMaterialUnit());
        btnMaterialAmount.setText(Util.fromFloat(material.getMaterialAmount()));

    }

    @Override
    public void onDetach() {
        navigationService.showActionBar();
        super.onDetach();
    }
}
