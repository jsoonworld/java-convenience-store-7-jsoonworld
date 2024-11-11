package store.controller;

import store.domain.Product;
import store.dto.request.StockRequest;
import store.dto.response.StockResponse;
import store.dto.response.StockResult;
import store.service.StockService;

import java.util.ArrayList;
import java.util.List;

public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    public StockResult handleRegularStock(String productName, int remainingQuantity, int productPrice, int requestedQuantity) {
        StockRequest stockRequest = StockRequest.of(productName, remainingQuantity, productPrice, requestedQuantity);

        StockResponse stockResponse = stockService.handleRegularStock(stockRequest);

        int totalPromotionalDiscount = 0;
        List<Product> productsToSave = new ArrayList<>();

        if (stockResponse.getUpdatedProduct() != null) {
            productsToSave.add(stockResponse.getUpdatedProduct());
            totalPromotionalDiscount += stockResponse.getDiscountAmount();
        }

        return StockResult.of(totalPromotionalDiscount, productsToSave);
    }
}
