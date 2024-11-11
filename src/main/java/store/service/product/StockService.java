package store.service.product;

import store.domain.Product;
import store.dto.request.StockRequest;
import store.dto.response.StockResponse;
import store.repository.ProductRepository;

public class StockService {

    private final ProductRepository productRepository;

    public StockService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public StockResponse handleRegularStock(StockRequest request) {
        int remainingQuantity = request.getRemainingQuantity();
        Product regularProduct = productRepository.findByNameAndPromotion(request.getProductName(), "null");

        if (remainingQuantity > 0 && regularProduct != null && regularProduct.getQuantityValue() >= remainingQuantity) {
            regularProduct = regularProduct.decreaseQuantity(remainingQuantity);

            int discountAmount = request.getProductPrice() * (request.getRequestedQuantity() - remainingQuantity);

            return StockResponse.of(remainingQuantity, discountAmount, regularProduct);
        }
        return StockResponse.emptyResponse();
    }
}
