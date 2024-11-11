package store.service.promotion;

import store.domain.product.Product;
import store.domain.vo.Price;
import store.domain.vo.ProductName;
import store.domain.vo.PromotionName;
import store.domain.vo.Quantity;
import store.dto.init.PromotionCalculationData;
import store.dto.request.promotion.PromotionRequest;

import java.util.List;

public class PromotionStockManager {

    public static void addProducts(PromotionCalculationData dto, PromotionRequest request) {
        List<Product> productsToSave = dto.getProductsToSave();
        String productName = request.getProductName();
        int productPrice = request.getProductPrice();
        int unmetPromotionQuantity = dto.getFinalQuantity() - request.getRequestedQuantity();
        int freeItems = dto.getFreeItems();

        Product additionalProduct = Product.of(ProductName.from(productName), Price.from(productPrice), Quantity.from(unmetPromotionQuantity), PromotionName.from("null"));
        productsToSave.add(additionalProduct);

        Product freeProduct = Product.of(ProductName.from(productName), Price.from(productPrice), Quantity.from(freeItems), PromotionName.from("null"));
        productsToSave.add(freeProduct);
        dto.getPromotionalDetails().append(String.format("%-10s\t%d\n", freeProduct.getName(), freeItems));
    }

    public static void processRemainingStock(PromotionCalculationData dto, PromotionRequest request) {
        List<Product> productsToSave = dto.getProductsToSave();
        String productName = request.getProductName();
        int productPrice = request.getProductPrice();
        int totalRequiredQuantity = dto.getTotalRequiredQuantity();
        int promoStock = dto.getPromoStock();
        int freeItems = dto.getFreeItems();

        Product promotionalProduct = Product.of(ProductName.from(productName), Price.from(productPrice), Quantity.from(promoStock), PromotionName.from("Promotion"));

        int quantityToDecrease = Math.min(promoStock, totalRequiredQuantity);
        promotionalProduct = promotionalProduct.decreaseQuantity(quantityToDecrease);
        productsToSave.add(promotionalProduct);
        dto.getPromotionalDetails().append(String.format("%-10s\t%d\n", promotionalProduct.getName(), freeItems));
    }
}
