package com.go.kchin.interfaces;

import com.go.kchin.models.Sale;

import java.util.List;

/**
 * Created by MAV1GA on 22/11/2016.
 */

public interface SalesService {

    void addToSale(Sale sale);
    List<Sale> getCurrentSale();
    void undoWithProductId(long productId);
    void undoWithPackageId(long packageId);
    void applySale();
}
