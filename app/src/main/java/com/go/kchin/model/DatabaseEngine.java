package com.go.kchin.model;

import android.util.Log;

import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Combo;
import com.go.kchin.model.database.Department;
import com.go.kchin.model.database.Material;
import com.go.kchin.model.database.Product;
import com.go.kchin.model.database.ProductsInCombo;
import com.go.kchin.model.database.Recipe;
import com.go.kchin.model.database.Sale;
import com.go.kchin.model.database.SaleTicket;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class DatabaseEngine implements MainMVP.ModelOps{

    private static DatabaseEngine instance;

    private static MainMVP.RequiredPresenterOps mPresenter;
    private static MainMVP.PreferenceAccess mPreferencePresenter;

    public static DatabaseEngine getInstance(MainMVP.RequiredPresenterOps presenter){
        if (instance == null){
            instance = new DatabaseEngine();
        }
        instance.setPresenter(presenter);
        return instance;
    }

    private void setPresenter(MainMVP.RequiredPresenterOps presenter){
        mPresenter = presenter;
    }

    private void setPreferencesPresenter(MainMVP.PreferenceAccess presenter) { mPreferencePresenter = presenter;}

    private DatabaseEngine (){}


    @Override
    public void addMaterial(Material newMaterial) {
        final long operationId = newMaterial.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.material_saved),
                operationId);
    }

    @Override
    public List<Material> getAllMaterials() {
        return Material.listAll(Material.class);
    }

    @Override
    public Material getMaterialFromId(long materialId) {
        return Material.findById(Material.class, materialId);
    }

    @Override
    public void updateMaterial(Material newMaterialParams) {
        Material material = Material.findById(Material.class, newMaterialParams.getId());
        material.materialMeasure = newMaterialParams.materialMeasure;
        material.materialName = newMaterialParams.materialName;
        material.materialPurchaseCost = newMaterialParams.materialPurchaseCost;
        material.materialRemaining = newMaterialParams.materialRemaining;
        material.materialStatus = newMaterialParams.materialStatus;
        material.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.saved));
    }

    @Override
    public void activateMaterial(long materialId) {
        Material material = Material.findById(Material.class, materialId);
        material.materialStatus = 1;
        material.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.active));
    }

    @Override
    public void deactivateMaterial(long materialId) {
        Material material = Material.findById(Material.class, materialId);
        material.materialStatus = 0;
        material.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.deactivated));
    }

    @Override
    public List<Material> getMaterials(String query) {
        String formatedSearchQuery = formatForQuery(query);
        return Material.find(Material.class, "material_name LIKE ?", "%"+formatedSearchQuery+"%");
    }

    public List<Material> getMaterialsFromProduct(long productId) {
        return Material.findWithQuery(
                Material.class, mPresenter.getStringResource(R.string.q_get_recipe_from_product),
                String.valueOf(productId));
    }

    @Override
    public void updateRecipe(long recipeId, float newAmount) {
        Recipe recipe = Recipe.findById(Recipe.class, recipeId);
        recipe.MaterialAmount = newAmount;
        recipe.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.saved));
    }

    private List<Recipe> getRecipeListFromProduct(long productId){
        return Recipe.find(Recipe.class, "product = ?", String.valueOf(productId));
    }

    @Override
    public void addProduct(Product newProduct) {
        final long operationId =newProduct.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.product_saved),
                operationId);
    }

    @Override
    public void updateProduct(Product product) {
        product.save();
        mPresenter.onOperationSuccess(R.string.saved);
    }

    @Override
    public void addMaterialToRecipe(long aLong, Material item) {
        Recipe recipe = new Recipe();
        recipe.MaterialAmount = 1.0f;
        recipe.product = getProduct(aLong);
        recipe.material = item;
        recipe.save();
        mPresenter.onOperationSuccess(R.string.saved);
    }

    @Override
    public void setProductDepartment(long aLong, Department item) {
        Product product = Product.findById(Product.class, aLong);
        product.department = item;
        updateProduct(product);
    }

    @Override
    public void buyProduct(long productId, float purchaseAmount) {
        Product product = Product.findById(Product.class, productId);
        product.productRemaining += purchaseAmount;
        if (product.madeOnSell == Product.PRODUCT_MADE_AND_STORE){
            for(Recipe r : getRecipeListFromProduct(product.getId())){
                Material m = r.material;
                m.materialRemaining -= (r.MaterialAmount*purchaseAmount);
                m.save();
            }
        }
        product.save();
        mPresenter.onOperationSuccess(R.string.operation_complete);
    }

    @Override
    public List<Product> getAllProducts() {
        return Product.listAll(Product.class);
    }

    @Override
    public List<Product> getProductsFromCombo(long comboId) {
        return Product.findWithQuery(Product.class, mPresenter.
                getStringResource(R.string.q_get_products_in_combo),
                String.valueOf(comboId));

    }

    @Override
    public List<Product> getProductsFromMaterial(long materialId) {
        return Product.findWithQuery(Product.class, mPresenter.getStringResource(
                R.string.q_get_products_from_material)
        );
    }

    @Override
    public Product getProduct(long productId) {
        return Product.findById(Product.class, productId);
    }

    @Override
    public List<Department> getAllDepartments() {
        List<Department> items = Department.listAll(Department.class);
        for (Department d : items){
            d.productsInDepartment = getProductsInDepartment(d.getId());
        }
        return items;
    }

    @Override
    public Department getDepartment(long departmentId) {
        Department department = Department.findById(Department.class, departmentId);
        department.productsInDepartment = getProductsInDepartment(departmentId);
        return department;
    }

    private long getProductsInDepartment(long departmentId) {
        return Product.count(Product.class, "department = ?",
                new String[]{String.valueOf(departmentId)});
    }

    @Override
    public void addDepartment(Department newDepartment) {
        newDepartment.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.department_saved));
    }



    @Override
    public void updateDepartment(long departmentId, Department department) {
        Department newDepartment = Material.findById(Department.class, departmentId);
        newDepartment.departmentName = department.departmentName;
        newDepartment.departmentStatus = department.departmentStatus;
        newDepartment.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.saved));
    }


    @Override
    public List<Combo> getAllCombos() {
        return Combo.listAll(Combo.class);
    }

    @Override
    public void addCombo(Combo combo) {
        combo.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.combo_saved));
    }

    @Override
    public Combo getCombo(long comboId) {
        return Combo.findById(Combo.class, comboId);
    }

    @Override
    public void updateCombo(long comboId, Combo combo) {
        Combo newCombo = Combo.findById(Combo.class, comboId);
        newCombo.comboName = combo.comboName;
        newCombo.comboSellCost = combo.comboSellCost;
        newCombo.comboStatus = combo.comboStatus;
        newCombo.save();
    }

    @Override
    public void addProductToCombo(long comboId, long productId, float productAmount) {
        ProductsInCombo arg = new ProductsInCombo();
        arg.productAmount = productAmount;
        arg.product = Product.findById(Product.class, productId);
        arg.combo = Combo.findById(Combo.class, comboId);
        arg.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.saved));


    }

    @Override
    public void applySale(List<Sale> currentSale) {
        if (currentSale.size() == 0){
            mPresenter.onOperationError(mPresenter.getStringResource(R.string.cannot_apply_void_sale));
            return;
        }
        SaleTicket ticket = new SaleTicket();
        ticket.dateTime = DateTime.now().getMillis();
        float total = 0.0f;
        for (Sale s : currentSale){
            total += s.saleTotal;
            if(!mPreferencePresenter.isAllowingDepletedStokSales())
                if (s.product.productRemaining < s.productAmount){
                    mPresenter.onOperationError(mPresenter.getStringResource(R.string.too_few_in_stock_to_sell));
                    return;
                }
        }
        ticket.saleTotal = total;
        ticket.vendor = "Toad";
        ticket.save();
        for (Sale s : currentSale){
            s.saleTicket = ticket;
            s.save();
            if (mPreferencePresenter.isActiveTracking()) {
                if (s.product.productType == Product.PRODUCT_TYPE_BUY_AND_SELL ||
                        s.product.madeOnSell == Product.PRODUCT_MADE_AND_STORE)
                s.product.productRemaining -= s.productAmount;
                s.product.save();
            }
            if (s.product.madeOnSell == Product.PRODUCT_MADE_AND_SELL){
                if (!mPreferencePresenter.isAllowingDepletedProduction())
                for (Recipe r : getRecipeListFromProduct(s.product.getId())){
                    Material m = r.material;
                    if (r.MaterialAmount > m.materialRemaining &&
                            !mPreferencePresenter.isAllowingDepletedProduction()) {
                        mPresenter.onOperationError(mPresenter.getStringResource(R.string.not_enough_material));
                        return;
                    }
                        m.materialRemaining -= r.MaterialAmount;
                        m.save();
                }
            }
        }
        mPresenter.onOperationSuccess(R.string.sale_applied);
    }

    @Override
    public void onDestroy() {
        Log.d(getClass().getSimpleName(), "Called onDestroy() method");
        //TODO: On destroy operations
    }

    @Override
    public void onConfigurationChanged(MainMVP.RequiredPresenterOps presenter, MainMVP.PreferenceAccess
                                       preferencePresenter) {
        instance.setPresenter(presenter);
        instance.setPreferencesPresenter(preferencePresenter);

    }

    @Override
    public List<Product> getProducts(String query) {
        String formatedSearchQuery = formatForQuery(query);
        return Product.find(Product.class, "product_name LIKE ?", "%"+formatedSearchQuery+"%");
    }

    @Override
    public List<PurchaseOrder> getDepletedMaterials() {
        List<Material> materialItems = getAllMaterials();
        List<PurchaseOrder> purchaseList = new ArrayList<>();
        for (Material m : materialItems){
            if (m.materialRemaining <= 10 )
                purchaseList.add(PurchaseOrder.fromMaterial(m));
        }
        return purchaseList;
    }

    @Override
    public List<PurchaseOrder> getDepletedProducts() {
        List<Product> productlItems = getAllProducts();
        List<PurchaseOrder> purchaseList = new ArrayList<>();
        for (Product p : productlItems){
            if (p.productRemaining <= 10 )
                purchaseList.add(PurchaseOrder.fromProduct(p));
        }
        return purchaseList;
    }

    @Override
    public List<PurchaseOrder> getAllDepletedArticles() {
        List<PurchaseOrder> purchaseList = new ArrayList<>();
        purchaseList.addAll(getDepletedMaterials());
        purchaseList.addAll(getDepletedProducts());
        return purchaseList;
    }

    @Override
    public void buyMaterial(long purchaseId, float arg) {
        Material material = Material.findById(Material.class, purchaseId);
        material.materialRemaining += arg;
        material.save();
        mPresenter.onOperationSuccess(R.string.operation_complete);
    }

    @Override
    public List<Recipe> getRecipeFromProduct(long productId) {
        return getRecipeListFromProduct(productId);
    }

    @Override
    public List<SaleTicket> getTicketsFromDate(DateTime time){
        String millsAtMorning = String.valueOf(time.withTimeAtStartOfDay().getMillis());
        String millsAtMidnight = String.valueOf(time.plusDays(1).withTimeAtStartOfDay().getMillis());
        String params [] = {millsAtMorning, millsAtMidnight};
        return SaleTicket.find(SaleTicket.class, "date_time >= ? and date_time < ?", params);
    }

    @Override
    public List<Sale> getSalesInTicket(SaleTicket ticket){
        String ticketId = String.valueOf(ticket.getId());
        return Sale.find(Sale.class, "sale_ticket = ?", ticketId );
    }

    private static String formatForQuery(String rawQuery){
        return rawQuery.replace(" ", "%");
    }
}
