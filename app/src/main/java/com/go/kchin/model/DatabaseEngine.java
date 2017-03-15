package com.go.kchin.model;

import android.util.Log;

import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Combo;
import com.go.kchin.model.database.Department;
import com.go.kchin.model.database.Material;
import com.go.kchin.model.database.Product;
import com.go.kchin.model.database.ProductsInCombo;
import com.go.kchin.model.database.PurchaseOperation;
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
        if ((Material.find(Material.class, "material_name = ?", newMaterial.materialName)).
                size() != 0){
            mPresenter.onOperationError(mPresenter.getStringResource(R.string.duplicate_material));
            return;
        }
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
        if ((Material.find(Material.class, "material_name = ?", newMaterialParams.materialName)).
                size() > 1){
            mPresenter.onOperationError(mPresenter.getStringResource(R.string.duplicate_material));
            return;
        }
        newMaterialParams.save();
        //mPresenter.on(mPresenter.getStringResource(R.string.saved));
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
        if (newAmount == 0){
            recipe.delete();
            return;
        }
        recipe.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.saved));
    }

    private List<Recipe> getRecipeListFromProduct(long productId){
        return Recipe.find(Recipe.class, "product = ?", String.valueOf(productId));
    }

    @Override
    public void addProduct(Product newProduct) {

        //Make sure the Product does not exists yet

        if ( (Product.find(Product.class, "product_name = ?", newProduct.productName).size() != 0)){
            mPresenter.onOperationError(mPresenter.getStringResource(R.string.duplicate_product));
            return;
        }

        newProduct.productType = Product.PRODUCT_TYPE_STORED;
        newProduct.productMeasureUnit = Product.MEASURE_PIECE;
        final long operationId =newProduct.save();

        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.product_saved),
                operationId);
    }

    @Override
    public void updateProduct(Product product) {
        if ( (Product.find(Product.class, "product_name = ?", product.productName).size() > 1)){
            mPresenter.onOperationError(mPresenter.getStringResource(R.string.duplicate_product));
            return;
        }
        product.save();
        //mPresenter.onOperationSuccess(R.string.saved);
    }

    @Override
    public void addMaterialToRecipe(long productId, Material item) {
        for (Recipe r : getRecipeFromProduct(productId)){
            if (r.material.getId() == item.getId()){
                mPresenter.onOperationError(mPresenter.getStringResource(R.string.duplicate_recipe));
                return;
            }
        }
        Recipe recipe = new Recipe();
        recipe.MaterialAmount = 1.0f;
        recipe.product = getProduct(productId);
        recipe.material = item;
        recipe.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.added_to_recipe));
    }

    @Override
    public void addMaterialToRecipe(long productId, long materialId) {
        Material m = getMaterialFromId(materialId);
        addMaterialToRecipe(productId, m);
    }

    @Override
    public void setProductDepartment(long aLong, Department item) {
        Product product = Product.findById(Product.class, aLong);
        product.department = item;
        product.save();
        mPresenter.onOperationSuccess(R.string.saved);
    }

    @Override
    public void buyProduct(long productId, float purchaseAmount, float purchaseCost) {
        Product product = Product.findById(Product.class, productId);
        if (product.productType == Product.PRODUCT_TYPE_MADE_ON_SALE){
            mPresenter.onOperationError(mPresenter.getStringResource(R.string.cannot_make_product));
            return;
        }

        if(!canMake(product)) {
            mPresenter.onOperationError(mPresenter.getStringResource(R.string.not_enough_material));
            return;
        }

        for (Recipe r : getRecipeListFromProduct(product.getId())) {
            Material m = r.material;
            m.materialRemaining -= (r.MaterialAmount * purchaseAmount);
            m.save();
        }

        product.productRemaining += purchaseAmount;
        product.save();

        //Now lets save to the purchases

        PurchaseOperation operation = new PurchaseOperation();
        operation.purchaseItems = purchaseAmount;
        operation.purchaseConcept = product.productName;
        operation.purchaseDateTime = DateTime.now().getMillis();
        operation.purchaseAmount = purchaseCost;
        operation.save();

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

    private boolean isValidSale(List<Sale> currentSale){
        for (Sale s : currentSale){
            if (!hasEnoughToSell(s)){
                mPresenter.onOperationError(mPresenter.getStringResource(R.string.stock_too_low));
                return false;
            }
            if (!canMake(s.product)){
                mPresenter.onOperationError(mPresenter.getStringResource(R.string.not_enough_material));
                return false;
            }

        }
        return true;
    }

    @Override
    public void applySale(List<Sale> currentSale) {



        if (currentSale.size() == 0){
            mPresenter.onOperationError(mPresenter.getStringResource(R.string.cannot_apply_void_sale));
            return;
        }

        if (!isValidSale(currentSale)){
            return;
        }

        SaleTicket ticket = new SaleTicket();
        ticket.dateTime = DateTime.now().getMillis();
        float total = 0.0f;
        for (Sale s : currentSale){
            total += s.saleTotal;
        }
        ticket.saleTotal = total;
        ticket.vendor = "Toad";
        ticket.save();
        for (Sale s : currentSale) {
            s.saleTicket = ticket;
            s.save();
            if (s.product.productType == Product.PRODUCT_TYPE_STORED) {
                s.product.productRemaining -= s.productAmount;
                s.product.save();
            }else {
                for (Recipe r : getRecipeListFromProduct(s.product.getId())) {
                    Material m = r.material;
                    m.materialRemaining -= r.MaterialAmount;
                    m.save();
                }
            }
        }
        mPresenter.onOperationSuccess(R.string.sale_applied);
    }

    private boolean canMake(Product product){
        if (mPreferencePresenter.isAllowingDepletedProduction() || product.productType ==
                Product.PRODUCT_TYPE_STORED)
            return true;
        for (Recipe r : getRecipeListFromProduct(product.getId())){
            Material m = r.material;
            if (r.MaterialAmount >= m.materialRemaining)
                return false;
        }
        return true;
    }

    private boolean hasEnoughToSell(Sale sale){
        return mPreferencePresenter.isAllowingDepletedProduction() || sale.product.productRemaining >= sale.productAmount
                || sale.product.productType == Product.PRODUCT_TYPE_MADE_ON_SALE;
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
    public List<DepletedItem> getDepletedMaterials() {
        List<Material> materialItems = getAllMaterials();
        List<DepletedItem> purchaseList = new ArrayList<>();
        for (Material m : materialItems){
            if (m.materialRemaining <= 10 )
                purchaseList.add(DepletedItem.fromMaterial(m));
        }
        return purchaseList;
    }

    @Override
    public List<DepletedItem> getDepletedProducts() {
        List<Product> productlItems = Product.find(Product.class, "product_type = ? " +
                "AND product_remaining <= ?", "1", "10");
        List<DepletedItem> purchaseList = new ArrayList<>();
        for (Product p : productlItems){
            if (p.productRemaining <= 10 )
                purchaseList.add(DepletedItem.fromProduct(p));
        }
        return purchaseList;
    }

    @Override
    public List<DepletedItem> getAllDepletedArticles() {
        List<DepletedItem> purchaseList = new ArrayList<>();
        purchaseList.addAll(getDepletedMaterials());
        purchaseList.addAll(getDepletedProducts());
        return purchaseList;
    }

    @Override
    public void buyMaterial(long purchaseId, float arg, float purchaseCost) {
        Material material = Material.findById(Material.class, purchaseId);
        material.materialRemaining += arg;
        material.save();

        //Add it to purchases

        PurchaseOperation operation = new PurchaseOperation();
        operation.purchaseItems = arg;
        operation.purchaseConcept = material.materialName;
        operation.purchaseAmount = purchaseCost;
        operation.purchaseDateTime = DateTime.now().getMillis();
        operation.save();

        mPresenter.onOperationSuccess(R.string.operation_complete);
    }

    @Override
    public List<Recipe> getRecipeFromProduct(long productId) {
        return getRecipeListFromProduct(productId);
    }

    @Override
    public List<Product> getProductsFromDepartment(long departmentId) {
        if (departmentId == -1){
            List<Product> allProducts = Product.listAll(Product.class);
            List<Product> pWithoutDepartment = new ArrayList<>();
            for (Product p : allProducts){
                if (p.department == null)
                    pWithoutDepartment.add(p);
            }
            return pWithoutDepartment;
        }

        return Product.find(Product.class, "department = ?", String.valueOf(departmentId));
    }

    @Override
    public List<Product> getProductsFromDepartment(long departmentId, String query) {
        String formatedSearchQuery = formatForQuery(query);
        return Product.find(Product.class, "material_name LIKE ?", "%"+formatedSearchQuery+"%");
    }

    @Override
    public void setProductInventory(long productId, float arg) {
        Product p = Product.findById(Product.class, productId);
        if (p != null){
            p.productRemaining = arg;
            p.save();
            mPresenter.onOperationSuccess(R.string.operation_complete);
        }

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

    @Override
    public List<PurchaseOperation> getPurchases(DateTime dateTime) {
        String millsAtMorning = String.valueOf(dateTime.withTimeAtStartOfDay().getMillis());
        String millsAtMidnight = String.valueOf(dateTime.plusDays(1).withTimeAtStartOfDay().getMillis());
        String params[] = {millsAtMorning, millsAtMidnight};
        return PurchaseOperation.find(PurchaseOperation.class, "purchase_date_time > ? and purchase_date_time < ?",
                params);
    }

    private static String formatForQuery(String rawQuery){
        return rawQuery.replace(" ", "%");
    }

    public static void prepare() {
        instance = new DatabaseEngine();
    }
}
