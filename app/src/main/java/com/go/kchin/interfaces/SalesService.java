package com.go.kchin.interfaces;

import com.go.kchin.models.Sale;

import java.util.List;

/**
 * Created by MAV1GA on 22/11/2016.
 */

public interface SalesService {

    void addToSale(Sale sale);

    void undoWithProductId(long productId);

    void returnSale(Sale sale);

    void applySale(List<Sale> sale);

    List<Sale> getCurrentSale();

    void cancelSale();
}
