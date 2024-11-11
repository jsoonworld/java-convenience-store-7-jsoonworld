package store.service;

import static store.exception.ErrorMessage.EXCEEDS_STOCK;

import store.domain.Product;
import store.domain.Promotion;
import store.dto.request.PromotionRequest;
import store.dto.request.PromotionPurchaseRequest;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

import java.util.List;

public class PromotionRequestCreator {


    public PromotionRequest createPromotionRequest(Product product, PromotionPurchaseRequest requestData, ProductRepository productRepository, PromotionRepository promotionRepository) {
        validateStock(requestData.getRequestedQuantity(), product);


        Product isPromotionProduct = productRepository.findProductByPromotionNameAndPromotion(product.getName());
        int buyQuantity = 0;
        int freeQuantity = 0;

        if (isPromotionProduct != null) {
            List<Promotion> promotionsByName = promotionRepository.findPromotionsByName(isPromotionProduct.getPromotionName());
            Promotion promotion = promotionsByName.get(0);
            buyQuantity = promotion.getBuyQuantity();
            freeQuantity = promotion.getFreeQuantity();
        }
        return PromotionRequest.of(
                product.getName(),
                requestData.getRequestedQuantity(),
                product.getPriceValue(),
                buyQuantity,
                freeQuantity,
                product.getQuantityValue(),
                product.getPromotionName()
        );
    }

    private void validateStock(int requestedQuantity, Product product) {
        if (requestedQuantity > product.getQuantityValue()) {
            throw new IllegalArgumentException(EXCEEDS_STOCK.getMessage());
        }
    }
}